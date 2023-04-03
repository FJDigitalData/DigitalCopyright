package com.ruoyi.web.controller.business;

import com.ruoyi.amazon.spapi.api.FeedsApi;
import com.ruoyi.amazon.spapi.client.ApiException;
import com.ruoyi.amazon.spapi.client.Pair;
import com.ruoyi.amazon.spapi.model.orders.GetOrderResponse;
import com.ruoyi.amazon.spapi.model.orders.GetOrdersResponse;
import com.ruoyi.amazon.spapi.model.orders.OrderList;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.UlidUtil;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.amazon.ApiBuildService;
import com.ruoyi.system.domain.AmazonAuthority;
import com.ruoyi.system.domain.AmazonOrder;
import com.ruoyi.system.domain.AmazonStoreMarket;
import com.ruoyi.system.domain.Order;
import com.ruoyi.system.domain.dto.OrderDTO;
import com.ruoyi.system.domain.vo.OrderVO;
import com.ruoyi.system.domain.vo.StoreAuthorityVO;
import com.ruoyi.system.mapper.AmazonAuthorityMapper;
import com.ruoyi.system.mapper.AmazonOrderMapper;
import com.ruoyi.system.mapper.AmazonStoreMarketMapper;
import com.ruoyi.system.mapper.MarketplaceMapper;
import com.ruoyi.system.service.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ch.qos.logback.core.joran.action.ActionConst.NULL;

/**
 * @author WangJiang
 * @since 2023/1/6 13:57
 */
@Tag(name = "订单接口")
@RestController
@RequestMapping("/business/order")
public class OrderController extends BaseController {

    @Resource
    private IOrderService iOrderService;

    @Resource
    private ApiBuildService apiBuildService;

    @Resource
    private AmazonAuthorityMapper amazonAuthorityMapper;
    @Resource
    private AmazonOrderMapper amazonOrderMapper;
    @Resource
    private AmazonStoreMarketMapper amazonStoreMarketMapper;
    @PostMapping
    @Operation(summary = "新增订单")
    @PreAuthorize("@ss.hasPermi('business:order:add')")
    public R<OrderVO> add(@RequestBody @Validated OrderDTO order) {
        return R.ok(iOrderService.add(order));
    }

    @PutMapping("update/{id}")
    @Operation(summary = "修改订单", parameters = {
            @Parameter(name = "id", description = "订单id", in = ParameterIn.PATH, schema = @Schema(type = "string"), required = true)
    })
    @PreAuthorize("@ss.hasPermi('business:order:edit')")
    public R<OrderVO> update(@PathVariable("id") String id,
                             @RequestBody @Validated OrderDTO order) {
        return R.ok(iOrderService.update(id, order));
    }

    @PutMapping("delivery/{id}")
    @Operation(summary = "订单发货", parameters = {
            @Parameter(name = "id", description = "订单id", in = ParameterIn.PATH, schema = @Schema(type = "string"), required = true),
            @Parameter(name = "expressName", description = "物流公司", in = ParameterIn.QUERY, schema = @Schema(type = "string"), required = true),
            @Parameter(name = "expressId", description = "物流单号", in = ParameterIn.QUERY, schema = @Schema(type = "string"), required = true),
    })
    @PreAuthorize("@ss.hasPermi('business:order:delivery')")
    public R<Boolean> delivery(@PathVariable("id") String id,
                               @RequestParam String expressName,
                               @RequestParam String expressId) {
        return R.ok(iOrderService.delivery(id, expressName, expressId));
    }

    @PutMapping("/delete/{id}")
    @Operation(summary = "删除订单", parameters = {
            @Parameter(name = "id", description = "订单id", in = ParameterIn.PATH, schema = @Schema(type = "string"), required = true)
    })
    @PreAuthorize("@ss.hasPermi('business:order:delete')")
    public R<Boolean> delete(@PathVariable("id") String id) {
        return R.ok(iOrderService.delete(id));
    }

    @PutMapping("/cancel/{id}")
    @Operation(summary = "取消订单", parameters = {
            @Parameter(name = "id", description = "订单id", in = ParameterIn.PATH, schema = @Schema(type = "string"), required = true)
    })
    @PreAuthorize("@ss.hasPermi('business:order:cancel')")
    public R<Boolean> cancel(@PathVariable("id") String id) {
        return R.ok(iOrderService.cancel(id));
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "查询订单详情", parameters = {
            @Parameter(name = "id", description = "订单id", in = ParameterIn.PATH, schema = @Schema(type = "string"), required = true)
    })
    @PreAuthorize("@ss.hasPermi('business:order:getDetail')")
    public R<OrderVO> getDetail(@PathVariable("id") String id) {
        return R.ok(iOrderService.getDetail(id));
    }

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermi('business:order:get')")
    @Operation(summary = "查询订单分页列表-需要登录", description = " 电商售卖方：只能看到自己的 运营人员：全部", parameters = {
            @Parameter(name = "id", description = "订单id/第三方订单id", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "type", description = "订单状态类型 0: 未支付、1:已支付、2:生产中、3:待发货、4:已发货、5:已完成、6:已取消", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "pageNum", description = "页码 默认：1", schema = @Schema(type = "integer", defaultValue = "1"), in = ParameterIn.QUERY),
            @Parameter(name = "pageSize", description = "每页数量 默认：10", schema = @Schema(type = "integer", defaultValue = "10"), in = ParameterIn.QUERY)
    })
    public TableDataInfo<Order> getPage(@RequestParam(required = false) String id,
                                        @RequestParam(required = false) String type,
                                        @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                        @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return iOrderService.getPage(id, type, pageNum, pageSize);
    }

    @Operation(summary = "获取订单导入模板(暂时不做)", hidden = true)
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<OrderVO> util = new ExcelUtil<OrderVO>(OrderVO.class);
        util.importTemplateExcel(response, "用户数据");
    }

    @Operation(summary = "亚马逊订单测试", parameters = {
            @Parameter(name = "orderId", description = "订单id", in = ParameterIn.PATH, schema = @Schema(type = "string"), required = true)
    })
    @GetMapping("/amazon-order/{orderId}")
    public R<GetOrderResponse> getAmazonOrder(@PathVariable("orderId") String orderId) {
        //第一步 根据店铺ID 查询授权信息对象
        AmazonAuthority amazonAuthority = amazonAuthorityMapper.getByStoreId("4");
            //    amazonAuthorityMapper.getByStoreId("3");getById(1630468492467994626L)
        GetOrderResponse response = new GetOrderResponse();
        try {
            //根据sku获取商品
            //ListingsApi api = apiBuildService.getProductApi(amazonAuthority);
            //Item response = api.getListingsItem(amazonAuthority.getSellerId(),
            //                "shop-e6fd3yslgf9v", Collections.singletonList("ATVPDKIKX0DER"), null, null);
            //根据订单id获取订单详情 111-0029796-8820240

            //第二步获取具体api
            //FeedsApi feedsApi = apiBuildService.getFeedApi(amazonAuthority);
            //第三部调用API
            //feedsApi.createFeedDocument()

            response = apiBuildService.getOrdersV0Api(amazonAuthority).getOrder(orderId);
        } catch (ApiException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return R.ok(response);
    }

//    @Operation(summary = "亚马逊订单列表测试", parameters = {
//            @Parameter(name = "marketplaceId", description = "marketplaceId", in = ParameterIn.QUERY, schema = @Schema(type = "String",defaultValue = "ATVPDKIKX0DER"), required = true),
//            @Parameter(name = "createdAfter", description = "在创建时间后", in = ParameterIn.QUERY, schema = @Schema(type = "String"), required = true)
//
//    })
//    @PostMapping("/amazon-order/getOrders")
//    public R<GetOrdersResponse> getAmazonOrders(
//            @RequestParam  String marketplaceId,
//            @RequestParam String createdAfter  ) {
//        //第一步 获取授权id与对应的店铺id
//        List<Long> authIdList  = new ArrayList<Long>();
//        List<String> shopIdList  = new ArrayList<String>();
//        List<StoreAuthorityVO> amazonAuthorityList =  amazonAuthorityMapper.list(null,null);
//        for (int i = 0; i < amazonAuthorityList.size(); i++) {
//            Long authId = amazonAuthorityList.get(i).getId();
//            authIdList.add(authId);
//            String shopId = amazonAuthorityList.get(i).getShopId();
//            shopIdList.add((shopId));
//        }
//        //第二步
//        for (int index = 0; index <authIdList.size() ; index++) {
//            AmazonAuthority amazonAuthority = amazonAuthorityMapper.getById(authIdList.get(index));
//            List<AmazonStoreMarket> marketList = amazonStoreMarketMapper.getByShopId(shopIdList.get(index));
//            List<String> marketplaceIds=new ArrayList<String>();
//
//            for (AmazonStoreMarket temp:marketList) {
//                marketplaceIds.add(temp.getMarketplaceId());
//            }
//            System.out.println(marketplaceIds);
//            //    amazonAuthorityMapper.getByStoreId("3");
//            String result = "";
//            GetOrdersResponse returnData = new GetOrdersResponse();
//            try {
//
//                String createdBefore=null;
//                String lastUpdatedAfter=null;
//                String lastUpdatedBefore=null;
//                List<String> orderStatuses=null;
//                List<String> fulfillmentChannels=null;
//                List<String> paymentMethods=null;
//                String buyerEmail=null;
//                String sellerOrderId=null;
//                Integer maxResultsPerPage=null;
//                List<String> easyShipShipmentStatuses=null;
//                String nextToken=null;
//                List<String> amazonOrderIds=null;
//                String actualFulfillmentSupplySourceId=null;
//                Boolean isISPU=null;
//                String storeChainStoreId=null;
//                System.out.println(amazonAuthority);
//                System.out.println(marketplaceIds);
//                GetOrdersResponse response = apiBuildService.getOrdersV0Api(amazonAuthority).getOrders( marketplaceIds,createdAfter, createdBefore,lastUpdatedAfter,lastUpdatedBefore,  orderStatuses,  fulfillmentChannels,  paymentMethods,  buyerEmail, sellerOrderId,maxResultsPerPage, easyShipShipmentStatuses,nextToken,amazonOrderIds,actualFulfillmentSupplySourceId,isISPU,storeChainStoreId);
//                result = response.toString();
//                returnData = response;
//                System.out.println(returnData);
//            ArrayList<com.ruoyi.amazon.spapi.model.orders.Order> orderArrayList =  response.getPayload().getOrders();
//            for (int i = 0; i < orderArrayList.size(); i++) {
//                AmazonOrder amazonOrder = new AmazonOrder();
//                Order orderinfo = amazonOrderMapper.checkOrderIdUnique(orderArrayList.get(i).getAmazonOrderId());
//                if(orderinfo !=null)
//                    continue;
//                amazonOrder.setId(orderArrayList.get(i).getAmazonOrderId());
//                amazonOrder.setAuthId("22");
//
//                if(orderArrayList.get(i).getBuyerInfo().getBuyerEmail() != null)
//                    amazonOrder.setBuyerEmail(orderArrayList.get(i).getBuyerInfo().getBuyerEmail());
//                else{
//                    amazonOrder.setBuyerEmail("null");
//                }
//
//
//                if(orderArrayList.get(i).getBuyerInfo().getBuyerName() != null)
//                    amazonOrder.setBuyerName(orderArrayList.get(i).getBuyerInfo().getBuyerName());
//                else{
//                    amazonOrder.setBuyerName("null");
//                }
////
////
////                amazonOrder.setCba_displayable_shipping_label(orderArrayList.get(i).getCbaDisplayableShippingLabel());
//                amazonOrder.setCbaDisplayableShippingLabel("222");
//
//                if(orderArrayList.get(i).getEarliestDeliveryDate() != null)
//                    amazonOrder.setEarliestDeliveryDate(orderArrayList.get(i).getEarliestDeliveryDate());
//                else{
//                    amazonOrder.setEarliestDeliveryDate("null");
//                }
//
//                if(orderArrayList.get(i).getEarliestShipDate() != null)
//                    amazonOrder.setEarliestShipDate(orderArrayList.get(i).getEarliestShipDate());
//                else{
//                    amazonOrder.setEarliestShipDate("null");
//                }
//
////                amazonOrder.setEasy_ship_shipment_status(orderArrayList.get(i).getEasyShipShipmentStatus());
//                if(orderArrayList.get(i).getEasyShipShipmentStatus() != null)
//                    amazonOrder.setEasyShipShipmentStatus(orderArrayList.get(i).getEasyShipShipmentStatus());
//                else{
//                    amazonOrder.setEasyShipShipmentStatus("null");
//                }
//
//                amazonOrder.setFulfillmentChannel("as");
//                Date currentDate = new Date();
//                amazonOrder.setLastUpdateDate(currentDate);
//
//
//                if(orderArrayList.get(i).getLatestDeliveryDate() != null)
//                    amazonOrder.setLatestDeliveryDate(orderArrayList.get(i).getLatestDeliveryDate());
//                else{
//                    amazonOrder.setLatestDeliveryDate("null");
//                }
//
//                if(orderArrayList.get(i).getLatestShipDate() != null)
//                    amazonOrder.setLatestShipDate(orderArrayList.get(i).getLatestShipDate());
//                else{
//                    amazonOrder.setLatestShipDate("null");
//                }
//
//                amazonOrder.setIsBusinessOrder("1");
//
//                if(orderArrayList.get(i).getMarketplaceId() != null)
//                    amazonOrder.setMarketplaceId(orderArrayList.get(i).getMarketplaceId());
//                else{
//                    amazonOrder.setMarketplaceId("null");
//                }
//
//                if(orderArrayList.get(i).getNumberOfItemsShipped() != null)
//                    amazonOrder.setNumberOfItemsShipped(orderArrayList.get(i).getNumberOfItemsShipped().longValue());
//                else{
//                    amazonOrder.setNumberOfItemsShipped(-1L);
//                }
//                if(orderArrayList.get(i).getNumberOfItemsUnshipped() != null)
//                    amazonOrder.setNumberOfItemsUnshipped(orderArrayList.get(i).getNumberOfItemsUnshipped().longValue());
//                else{
//                    amazonOrder.setNumberOfItemsUnshipped(-1L);
//                }
//                if(orderArrayList.get(i).getOrderTotal().getAmount() != null)
//                    amazonOrder.setOrderTotalMoney( (long)(Double.parseDouble(orderArrayList.get(i).getOrderTotal().getAmount())));
//                else{
//                    amazonOrder.setOrderTotalMoney(-1L);
//                }
//                if(orderArrayList.get(i).getOrderTotal().getCurrencyCode() != null)
//                    amazonOrder.setOrderTotalMoneyCode(orderArrayList.get(i).getOrderTotal().getCurrencyCode());
//                else{
//                    amazonOrder.setOrderTotalMoneyCode("null");
//                }
//
//                amazonOrder.setOrderTotalMoneyCode(orderArrayList.get(i).getOrderTotal().getCurrencyCode());
//
//                if(orderArrayList.get(i).getOrderChannel() != null)
//                    amazonOrder.setOrderChannel(orderArrayList.get(i).getOrderChannel());
//                else{
//                    amazonOrder.setOrderChannel("null");
//                }
//                if(orderArrayList.get(i).getOrderType().getValue() != null)
//                    amazonOrder.setOrderType(orderArrayList.get(i).getOrderType().getValue());
//                else{
//                    amazonOrder.setOrderType("null");
//                }
//
//
//                if(orderArrayList.get(i).getPaymentMethod() != null)
//                    amazonOrder.setPaymentMethod(orderArrayList.get(i).getPaymentMethod().getValue());
//                else{
//                    amazonOrder.setPaymentMethod("null");
//                }
//
//
//                if(orderArrayList.get(i).getPromiseResponseDueDate() != null)
//                    amazonOrder.setPromiseResponseDueDate(orderArrayList.get(i).getPromiseResponseDueDate());
//                else{
//                    amazonOrder.setPromiseResponseDueDate("null");
//                }
//                amazonOrder.setPurchaseDate(currentDate);
//
//                if(orderArrayList.get(i).getSalesChannel() != null)
//                    amazonOrder.setSalesChannel(orderArrayList.get(i).getSalesChannel());
//                else{
//                    amazonOrder.setSalesChannel("null");
//                }
//
//                if(orderArrayList.get(i).getShipServiceLevel() != null)
//                    amazonOrder.setShipServiceLevel(orderArrayList.get(i).getShipServiceLevel());
//                else{
//                    amazonOrder.setShipServiceLevel("null");
//                }
//
//                if(orderArrayList.get(i).getShipmentServiceLevelCategory() != null)
//                    amazonOrder.setShipmentServiceLevelCategory(orderArrayList.get(i).getShipmentServiceLevelCategory());
//                else{
//                    amazonOrder.setShipmentServiceLevelCategory("null");
//                }
//
//                amazonOrder.setUpdateTime(currentDate);
//
//                if(orderArrayList.get(i).getSellerOrderId() != null)
//                    amazonOrder.setSellerOrderId(orderArrayList.get(i).getSellerOrderId());
//                else{
//                    amazonOrder.setSellerOrderId("null");
//                }
//
//                amazonOrder.setOrderStatus("1");
//
//                amazonOrderMapper.addOrder(amazonOrder);
//            }
//            } catch (ApiException e) {
//                logger.error(result);
//                throw new IllegalArgumentException(e.getMessage());
//            }
//        }
//
//        return R.ok();
//    }

}
