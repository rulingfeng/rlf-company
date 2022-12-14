package com.rlf.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.rlf.config.Constants;
import com.rlf.es.EsOrder;
import com.rlf.es.snow.KeyUtils;
import com.rlf.feign.GoodsApi;
import com.rlf.feign.StoreApi;
import com.rlf.model.OrderMain;
import com.rlf.service.OrderMainService;
import com.rlf.util.Result;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.StringUtils;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author rulingfeng
 * @time 2022/11/14 08:46
 * @desc
 */
@RestController
@RequestMapping("/test")
public class OrderController {
    @Resource
    private Constants constants;
    @Autowired
    private OrderMainService orderMainService;
    @Resource
    private GoodsApi goodsApi;
    @Resource
    private StoreApi storeApi;
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private RedissonClient redissonClient;
    @Autowired
    private RestHighLevelClient highLevelClient;

    @GetMapping("/aaa")
    public void aaa(){
        System.out.println(constants.getType());
    }
    @GetMapping("/redisson")
    public String redisson(){
        RLock creagggggg = redissonClient.getLock("creagggggg");
        creagggggg.lock();
        creagggggg.unlock();
        return creagggggg.getName();
    }

    @GetMapping("/bbb")
//    @GlobalTransactional(name = "fsp-create-order",rollbackFor = Exception.class)
    @ShardingTransactionType(TransactionType.BASE)
    @Transactional
    public Result<String> bbb(Integer type)throws IOException {
        EsOrder esOrder = new EsOrder();
        esOrder.setId(8888l);
        esOrder.setOrderNo(KeyUtils.getSnakeflakeStringKey());
        esOrder.setOrderName("??????????????????????????????????????????");
        esOrder.setPrice(19.6);
        esOrder.setStartTime(new Date());
        esOrder.setEndTime(new Date());
        // ????????????
        IndexRequest request = new IndexRequest("user_order");
        // ????????????id
        request.id("8888");

        // ???User???????????????JSON?????????????????????
        request.source(JSON.toJSONString(esOrder), XContentType.JSON);
        // ????????????????????????????????????
        IndexResponse index = highLevelClient.index(request, RequestOptions.DEFAULT);

        System.out.println(index.toString());
        // ????????????
        System.out.println(index.status());


        redisTemplate.opsForValue().set("20221128order",System.currentTimeMillis());
        ArrayList<OrderMain> objects = Lists.newArrayList();
        for (long i = 0; i < 4 ; i++) {
            OrderMain orderMain = new OrderMain();
            orderMain.setOrderId(888l);
            orderMain.setOrderNo(i);
            objects.add(orderMain);
        }
        orderMainService.saveBatch(objects);
        System.out.println("??????goods");
        Result<String> bbbb = goodsApi.bbbb(777l);
        System.out.println(bbbb);
        System.out.println("??????store");
        return storeApi.bbbb(666l,type);


    }

    @GetMapping("/esSearch")
    public void searchTest(@RequestParam(required = false) String orderNo,@RequestParam(required = false) String orderName) throws IOException {
        Integer pageNum=1;
        Integer pageSize=120;
        SearchRequest request = new SearchRequest();

        //??????????????????
        request.indices("user_order");

        // ????????????
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // ?????????????????? QueryBuilders ?????????
        // termQuery ????????????
        // ????????????????????????????????? .keyword????????? name.keyword
//        TermQueryBuilder termQuery = QueryBuilders.termQuery("title.keyword", "??????20");
        // ???????????? matchQuery ????????????
        // MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("title", "????????????");

        // sourceBuilder.query(matchQuery);
        //limit 0,10
        sourceBuilder.from((pageNum-1)*pageSize);
        sourceBuilder.size(pageSize);
        // ?????????????????????????????????
        // ?????????????????????????????????????????????



        //???????????????????????????
        //sourceBuilder.sort("id", SortOrder.DESC); //????????? order by id desc
        //??????????????????
        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));

        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //????????????
        String[] includes = {};  //????????? select name from index
        String[] excludes = {"price"};  //??????????????????
        sourceBuilder.fetchSource(includes,excludes);

//        QueryBuilders.boolQuery()????????????:
//        1. ??????must(QueryBuilder)          ?????????  ( QueryBuilder1 ) and ( QueryBuilder2 ) ??????must ??? ??? ??????
//        2. ??????should(QueryBuilder)        ?????????  ( QueryBuilder1 ) or  ( QueryBuilder2 )  ??????should ??? ??? ??????
//        3. ??????must/should                 ?????????  where xx = xx?????????must/should?????????QueryBuilder?????????????????????



        //?????????select * from table where title like '%??????%'  or brand = "inm16" or brand = "inm15"
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //must -> SQL ???=??? ???and???
        if(StringUtils.isNotBlank(orderNo)){
            boolQueryBuilder.must(QueryBuilders.matchQuery("orderNo", orderNo));
        }



        //?????????????????????
//        boolQueryBuilder.filter(QueryBuilders.rangeQuery("startTime").gte(1666245260450L));
//        boolQueryBuilder.filter(QueryBuilders.rangeQuery("endTime").lte(1666245268348L));

        // boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(5.1));

        //should -> SQL ???or???
        if(StringUtils.isNotBlank(orderName)){
            boolQueryBuilder.should(QueryBuilders.matchQuery("orderName", orderName));
        }




        sourceBuilder.query(boolQueryBuilder);



        // ??????????????? request
        request.source(sourceBuilder);


//        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));


        SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println(JSON.toJSONString(hits));
        System.out.println("????????????count:" + hits.getTotalHits().value);

        System.out.println(JSON.toJSONString(response));


        for (SearchHit hit : hits.getHits()) {
            EsOrder esGoods = JSONObject.parseObject(JSONObject.toJSONString(hit.getSourceAsMap()), EsOrder.class);
            System.out.println(JSONObject.toJSONString(esGoods)+hit.getScore());

        }
    }


}
