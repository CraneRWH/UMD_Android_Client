package com.ymd.client.utils;

import com.google.gson.Gson;
import com.ymd.client.R;
import com.ymd.client.model.bean.homePage.MerchantInfoEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by zhouweilong on 2016/12/27.
 */

public class DataUtils {

    public static String data="{\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"goodscatrgory\": {\n" +
            "                \"goodsitem\": [\n" +
            "                    {\n" +
            "                        \"name\": \"苹果\",\n" +
            "                        \"price\": 10,\n" +
            "                        \"introduce\": \"苹果好吃啊，很甜！\",\n" +
            "                        \"goodsImgUrl\": \"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1701109266,1081476103&fm=116&gp=0.jpg\",\n" +
            "                        \"moreStandard\": false\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"name\": \"香蕉\",\n" +
            "                        \"price\": 10,\n" +
            "                        \"introduce\": \"香蕉好吃啊，又大很甜！\",\n" +
            "                        \"goodsImgUrl\": \"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=4150969577,207675143&fm=116&gp=0.jpg\",\n" +
            "                        \"moreStandard\": false\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"name\": \"橘子\",\n" +
            "                        \"price\": 10,\n" +
            "                        \"introduce\": \"橘子非常好吃啊，很甜！\",\n" +
            "                        \"goodsImgUrl\": \"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=244020795,2296627467&fm=116&gp=0.jpg\",\n" +
            "                        \"moreStandard\": false\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"name\": \"榴莲\",\n" +
            "                        \"price\": 10,\n" +
            "                        \"introduce\": \"我对象喜欢吃榴莲！\",\n" +
            "                        \"goodsImgUrl\": \"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1241578183,1527853673&fm=116&gp=0.jpg\",\n" +
            "                        \"moreStandard\": false\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"name\": \"桃子\",\n" +
            "                        \"price\": 10,\n" +
            "                        \"introduce\": \"大龙家的桃子就是好吃！\",\n" +
            "                        \"goodsImgUrl\": \"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2983725700,2237376083&fm=116&gp=0.jpg\",\n" +
            "                        \"moreStandard\": false\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"name\": \"橙子\",\n" +
            "                        \"price\": 10,\n" +
            "                        \"introduce\": \"橙子非常好吃啊，很甜！\",\n" +
            "                        \"goodsImgUrl\": \"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2549573239,2448800271&fm=111&gp=0.jpg\",\n" +
            "                        \"moreStandard\": false\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"name\": \"梨\",\n" +
            "                        \"price\": 10,\n" +
            "                        \"introduce\": \"梨非常好吃啊，很甜！\",\n" +
            "                        \"goodsImgUrl\": \"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1966863908,2578898835&fm=116&gp=0.jpg\",\n" +
            "                        \"moreStandard\": false\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"name\": \"水果\"\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"goodscatrgory\": {\n" +
            "                \"goodsitem\": [\n" +
            "                    {\n" +
            "                        \"name\": \"菠菜\",\n" +
            "                        \"price\": 12,\n" +
            "                        \"introduce\": \"菠菜有营养蛮好吃的\",\n" +
            "                        \"goodsImgUrl\": \"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1090875894,2830536606&fm=116&gp=0.jpg\",\n" +
            "                        \"moreStandard\": false\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"name\": \"韭菜\",\n" +
            "                        \"price\": 10,\n" +
            "                        \"introduce\": \"韭菜好吃还能壮阳！\",\n" +
            "                        \"goodsImgUrl\": \"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3257122934,2954156923&fm=116&gp=0.jpg\",\n" +
            "                        \"moreStandard\": false\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"name\": \"生菜\",\n" +
            "                        \"price\": 10,\n" +
            "                        \"introduce\": \"卷饼必需品！\",\n" +
            "                        \"goodsImgUrl\": \"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3706196441,2863505692&fm=116&gp=0.jpg\",\n" +
            "                        \"moreStandard\": false\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"name\": \"空心菜\",\n" +
            "                        \"price\": 10,\n" +
            "                        \"introduce\": \"多放点辣椒也蛮好吃的！\",\n" +
            "                        \"goodsImgUrl\": \"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1884106436,247345386&fm=116&gp=0.jpg\",\n" +
            "                        \"moreStandard\": false\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"name\": \"芹菜\",\n" +
            "                        \"price\": 10,\n" +
            "                        \"introduce\": \"芹菜好吃降血压！\",\n" +
            "                        \"goodsImgUrl\": \"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3881529425,3695854192&fm=116&gp=0.jpg\",\n" +
            "                        \"moreStandard\": false\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"name\": \"白菜\",\n" +
            "                        \"price\": 10,\n" +
            "                        \"introduce\": \"白菜炖粉条好吃\",\n" +
            "                        \"goodsImgUrl\": \"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2051825384,1743284996&fm=116&gp=0.jpg\",\n" +
            "                        \"moreStandard\": false\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"name\": \"蔬菜\"\n" +
            "            }\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    /**
     * 转成list
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> GsonToList(String gsonString, Class<T[]> cls) {
        T[] arr = new Gson().fromJson(gsonString, cls);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }
    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = new Gson().fromJson(gsonString, cls);
        return t;
    }


    /**
     * 商铺数据
     * @return
     */
    public static List getMeachantData() {
        List<MerchantInfoEntity> datas = new ArrayList<>();
        MerchantInfoEntity entity = new MerchantInfoEntity();
        entity.setAddress("山东济南");
        entity.setCity("济南市");
        entity.setDiscount("8.6");
        entity.setDistance("884m");
        entity.setName("稻香居");
        entity.setScore("4.5");
        datas.add(entity);
        entity = new MerchantInfoEntity();
        entity.setAddress("山东济南");
        entity.setCity("济南市");
        entity.setDiscount("8.6");
        entity.setDistance("884m");
        entity.setName("沙县小吃");
        entity.setScore("4.5");
        datas.add(entity);
        entity = new MerchantInfoEntity();
        entity.setAddress("山东济南");
        entity.setCity("济南市");
        entity.setDiscount("8.6");
        entity.setDistance("884m");
        entity.setName("驴肉火烧");
        entity.setScore("4.5");
        datas.add(entity);
        return datas;
    }

    /**
     * 首页功能选项数据
     * @return
     */
    public static List<Map<String,Object>> getFunctionsData() {
        List<Map<String ,Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>(); map.put("name","美食"); map.put("icon", R.mipmap.food_item_icon); list.add(map);

        map = new HashMap<>(); map.put("name","酒店"); map.put("icon", R.mipmap.hospital_item_icon); list.add(map);

        map = new HashMap<>(); map.put("name","爱车"); map.put("icon", R.mipmap.car_item_icon); list.add(map);

        map = new HashMap<>(); map.put("name","美容美发"); map.put("icon", R.mipmap.meirong_item_icon); list.add(map);

        map = new HashMap<>(); map.put("name","电影"); map.put("icon", R.mipmap.movie_item_icon); list.add(map);

        map = new HashMap<>(); map.put("name","生鲜"); map.put("icon", R.mipmap.shengxian_item_icon); list.add(map);

        map = new HashMap<>(); map.put("name","金融");map.put("icon", R.mipmap.jinrong_item_icon);list.add(map);

        map = new HashMap<>(); map.put("name","洗浴/KTV");map.put("icon", R.mipmap.xiyu_item_icon);list.add(map);

        map = new HashMap<>(); map.put("name","优币专区"); map.put("icon", R.mipmap.ktv_item_icon); list.add(map);

        map = new HashMap<>(); map.put("name","其他分类");map.put("icon", R.mipmap.other_item_icon);list.add(map);

        return list;
    }


    /**
     * 食物列表
     * @return
     */
    public static List<Map<String,Object>> getFoodData() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 1);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 1);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 1);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 2);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 2);

        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 2);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 3);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 3);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 4);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 4);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 5);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 6);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 7);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 7);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 7);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 7);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 8);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 8);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 8);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 8);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 9);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 9);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 9);
        list.add(map);
        return  list;
    }

    /**
     * 食物类别
     * @return
     */
    public static List<Map<String,Object>> getFoodType() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "凉菜");
        map.put("isChoose",true);
        map.put("pid", 1);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "酒水");
        map.put("isChoose",false);
        map.put("pid", 2);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "热菜");
        map.put("isChoose",false);
        map.put("pid", 3);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "热销");
        map.put("isChoose",false);
        map.put("pid", 4);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "凉菜");
        map.put("isChoose",false);
        map.put("pid", 5);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "酒水");
        map.put("isChoose",false);
        map.put("pid", 6);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "热菜");
        map.put("isChoose",false);
        map.put("pid", 7);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "热销");
        map.put("isChoose",false);
        map.put("pid", 8);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "凉菜");
        map.put("isChoose",false);
        map.put("pid", 9);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "酒水");
        map.put("isChoose",false);
        map.put("pid", 10);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "热菜");
        map.put("isChoose",false);
        map.put("pid", 11);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "热销");
        map.put("isChoose",false);
        map.put("pid", 12);
        list.add(map);
        return list;
    }

    /**
     * 获取城市列表
     * @return
     */
    public static String getCityList() {
        return "[\n" +
                "    {\n" +
                "      \"cityID\": \"1529\",\n" +
                "      \"cityName\": \"阿拉善盟\",\n" +
                "      \"cityFirst\": \"A\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2103\",\n" +
                "      \"cityName\": \"鞍山市\",\n" +
                "      \"cityFirst\": \"A\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3408\",\n" +
                "      \"cityName\": \"安庆市\",\n" +
                "      \"cityFirst\": \"A\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4105\",\n" +
                "      \"cityName\": \"安阳市\",\n" +
                "      \"cityFirst\": \"A\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4611\",\n" +
                "      \"cityName\": \"定安县\",\n" +
                "      \"cityFirst\": \"A\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5132\",\n" +
                "      \"cityName\": \"阿坝藏族羌族自治州\",\n" +
                "      \"cityFirst\": \"A\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5204\",\n" +
                "      \"cityName\": \"安顺市\",\n" +
                "      \"cityFirst\": \"A\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5425\",\n" +
                "      \"cityName\": \"阿里地区\",\n" +
                "      \"cityFirst\": \"A\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6109\",\n" +
                "      \"cityName\": \"安康市\",\n" +
                "      \"cityFirst\": \"A\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6529\",\n" +
                "      \"cityName\": \"阿克苏地区\",\n" +
                "      \"cityFirst\": \"A\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6543\",\n" +
                "      \"cityName\": \"阿勒泰地区\",\n" +
                "      \"cityFirst\": \"A\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1306\",\n" +
                "      \"cityName\": \"保定市\",\n" +
                "      \"cityFirst\": \"B\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1502\",\n" +
                "      \"cityName\": \"包头市\",\n" +
                "      \"cityFirst\": \"B\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1508\",\n" +
                "      \"cityName\": \"巴彦淖尔市\",\n" +
                "      \"cityFirst\": \"B\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2105\",\n" +
                "      \"cityName\": \"本溪市\",\n" +
                "      \"cityFirst\": \"B\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2206\",\n" +
                "      \"cityName\": \"白山市\",\n" +
                "      \"cityFirst\": \"B\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2208\",\n" +
                "      \"cityName\": \"白城市\",\n" +
                "      \"cityFirst\": \"B\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3403\",\n" +
                "      \"cityName\": \"蚌埠市\",\n" +
                "      \"cityFirst\": \"B\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3716\",\n" +
                "      \"cityName\": \"滨州市\",\n" +
                "      \"cityFirst\": \"B\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4505\",\n" +
                "      \"cityName\": \"北海市\",\n" +
                "      \"cityFirst\": \"B\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4510\",\n" +
                "      \"cityName\": \"百色市\",\n" +
                "      \"cityFirst\": \"B\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4615\",\n" +
                "      \"cityName\": \"白沙县\",\n" +
                "      \"cityFirst\": \"B\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4619\",\n" +
                "      \"cityName\": \"保亭县\",\n" +
                "      \"cityFirst\": \"B\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5119\",\n" +
                "      \"cityName\": \"巴中市\",\n" +
                "      \"cityFirst\": \"B\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1100\",\n" +
                "      \"cityName\": \"北京市\",\n" +
                "      \"cityFirst\": \"B\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5224\",\n" +
                "      \"cityName\": \"毕节地区\",\n" +
                "      \"cityFirst\": \"B\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5305\",\n" +
                "      \"cityName\": \"保山市\",\n" +
                "      \"cityFirst\": \"B\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6103\",\n" +
                "      \"cityName\": \"宝鸡市\",\n" +
                "      \"cityFirst\": \"B\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6204\",\n" +
                "      \"cityName\": \"白银市\",\n" +
                "      \"cityFirst\": \"B\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6527\",\n" +
                "      \"cityName\": \"博尔塔拉蒙古自治州\",\n" +
                "      \"cityFirst\": \"B\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6528\",\n" +
                "      \"cityName\": \"巴音郭楞蒙古自治州\",\n" +
                "      \"cityFirst\": \"B\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1308\",\n" +
                "      \"cityName\": \"承德市\",\n" +
                "      \"cityFirst\": \"C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1309\",\n" +
                "      \"cityName\": \"沧州市\",\n" +
                "      \"cityFirst\": \"C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1404\",\n" +
                "      \"cityName\": \"长治市\",\n" +
                "      \"cityFirst\": \"C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1504\",\n" +
                "      \"cityName\": \"赤峰市\",\n" +
                "      \"cityFirst\": \"C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2201\",\n" +
                "      \"cityName\": \"长春市\",\n" +
                "      \"cityFirst\": \"C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3204\",\n" +
                "      \"cityName\": \"常州市\",\n" +
                "      \"cityFirst\": \"C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3411\",\n" +
                "      \"cityName\": \"滁州市\",\n" +
                "      \"cityFirst\": \"C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3414\",\n" +
                "      \"cityName\": \"巢湖市\",\n" +
                "      \"cityFirst\": \"C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3417\",\n" +
                "      \"cityName\": \"池州市\",\n" +
                "      \"cityFirst\": \"C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4301\",\n" +
                "      \"cityName\": \"长沙市\",\n" +
                "      \"cityFirst\": \"C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4307\",\n" +
                "      \"cityName\": \"常德市\",\n" +
                "      \"cityFirst\": \"C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4310\",\n" +
                "      \"cityName\": \"郴州市\",\n" +
                "      \"cityFirst\": \"C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4451\",\n" +
                "      \"cityName\": \"潮州市\",\n" +
                "      \"cityFirst\": \"C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4514\",\n" +
                "      \"cityName\": \"崇左市\",\n" +
                "      \"cityFirst\": \"C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4613\",\n" +
                "      \"cityName\": \"澄迈县\",\n" +
                "      \"cityFirst\": \"C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4616\",\n" +
                "      \"cityName\": \"昌江县\",\n" +
                "      \"cityFirst\": \"C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5000\",\n" +
                "      \"cityName\": \"重庆市\",\n" +
                "      \"cityFirst\": \"C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5101\",\n" +
                "      \"cityName\": \"成都市\",\n" +
                "      \"cityFirst\": \"C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5323\",\n" +
                "      \"cityName\": \"楚雄彝族自治州\",\n" +
                "      \"cityFirst\": \"C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5421\",\n" +
                "      \"cityName\": \"昌都地区\",\n" +
                "      \"cityFirst\": \"C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6523\",\n" +
                "      \"cityName\": \"昌吉回族自治州\",\n" +
                "      \"cityFirst\": \"C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1402\",\n" +
                "      \"cityName\": \"大同市\",\n" +
                "      \"cityFirst\": \"D\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2102\",\n" +
                "      \"cityName\": \"大连市\",\n" +
                "      \"cityFirst\": \"D\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2106\",\n" +
                "      \"cityName\": \"丹东市\",\n" +
                "      \"cityFirst\": \"D\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2306\",\n" +
                "      \"cityName\": \"大庆市\",\n" +
                "      \"cityFirst\": \"D\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2327\",\n" +
                "      \"cityName\": \"大兴安岭地区\",\n" +
                "      \"cityFirst\": \"D\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3705\",\n" +
                "      \"cityName\": \"东营市\",\n" +
                "      \"cityFirst\": \"D\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3714\",\n" +
                "      \"cityName\": \"德州市\",\n" +
                "      \"cityFirst\": \"D\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4419\",\n" +
                "      \"cityName\": \"东莞市\",\n" +
                "      \"cityFirst\": \"D\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4609\",\n" +
                "      \"cityName\": \"儋州市\",\n" +
                "      \"cityFirst\": \"D\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4610\",\n" +
                "      \"cityName\": \"东方市\",\n" +
                "      \"cityFirst\": \"D\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5106\",\n" +
                "      \"cityName\": \"德阳市\",\n" +
                "      \"cityFirst\": \"D\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5117\",\n" +
                "      \"cityName\": \"达州市\",\n" +
                "      \"cityFirst\": \"D\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5329\",\n" +
                "      \"cityName\": \"大理白族自治州\",\n" +
                "      \"cityFirst\": \"D\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5331\",\n" +
                "      \"cityName\": \"德宏傣族景颇族自治州\",\n" +
                "      \"cityFirst\": \"D\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5334\",\n" +
                "      \"cityName\": \"迪庆藏族自治州\",\n" +
                "      \"cityFirst\": \"D\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6211\",\n" +
                "      \"cityName\": \"定西市\",\n" +
                "      \"cityFirst\": \"D\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1506\",\n" +
                "      \"cityName\": \"鄂尔多斯市\",\n" +
                "      \"cityFirst\": \"E\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4207\",\n" +
                "      \"cityName\": \"鄂州市\",\n" +
                "      \"cityFirst\": \"E\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4228\",\n" +
                "      \"cityName\": \"恩施土家族苗族自治州\",\n" +
                "      \"cityFirst\": \"E\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2104\",\n" +
                "      \"cityName\": \"抚顺市\",\n" +
                "      \"cityFirst\": \"F\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2109\",\n" +
                "      \"cityName\": \"阜新市\",\n" +
                "      \"cityFirst\": \"F\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3412\",\n" +
                "      \"cityName\": \"阜阳市\",\n" +
                "      \"cityFirst\": \"F\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3501\",\n" +
                "      \"cityName\": \"福州市\",\n" +
                "      \"cityFirst\": \"F\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3610\",\n" +
                "      \"cityName\": \"抚州市\",\n" +
                "      \"cityFirst\": \"F\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4406\",\n" +
                "      \"cityName\": \"佛山市\",\n" +
                "      \"cityFirst\": \"F\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4506\",\n" +
                "      \"cityName\": \"防城港市\",\n" +
                "      \"cityFirst\": \"F\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3607\",\n" +
                "      \"cityName\": \"赣州市\",\n" +
                "      \"cityFirst\": \"G\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4401\",\n" +
                "      \"cityName\": \"广州市\",\n" +
                "      \"cityFirst\": \"G\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4503\",\n" +
                "      \"cityName\": \"桂林市\",\n" +
                "      \"cityFirst\": \"G\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4508\",\n" +
                "      \"cityName\": \"贵港市\",\n" +
                "      \"cityFirst\": \"G\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5108\",\n" +
                "      \"cityName\": \"广元市\",\n" +
                "      \"cityFirst\": \"G\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5116\",\n" +
                "      \"cityName\": \"广安市\",\n" +
                "      \"cityFirst\": \"G\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5133\",\n" +
                "      \"cityName\": \"甘孜藏族自治州\",\n" +
                "      \"cityFirst\": \"G\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5201\",\n" +
                "      \"cityName\": \"贵阳市\",\n" +
                "      \"cityFirst\": \"G\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6230\",\n" +
                "      \"cityName\": \"甘南藏族自治州\",\n" +
                "      \"cityFirst\": \"G\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6326\",\n" +
                "      \"cityName\": \"果洛藏族自治州\",\n" +
                "      \"cityFirst\": \"G\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6404\",\n" +
                "      \"cityName\": \"固原市\",\n" +
                "      \"cityFirst\": \"G\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1304\",\n" +
                "      \"cityName\": \"邯郸市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1311\",\n" +
                "      \"cityName\": \"衡水市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1501\",\n" +
                "      \"cityName\": \"呼和浩特市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1507\",\n" +
                "      \"cityName\": \"呼伦贝尔市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2114\",\n" +
                "      \"cityName\": \"葫芦岛市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2301\",\n" +
                "      \"cityName\": \"哈尔滨市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2304\",\n" +
                "      \"cityName\": \"鹤岗市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2311\",\n" +
                "      \"cityName\": \"黑河市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3208\",\n" +
                "      \"cityName\": \"淮安市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3301\",\n" +
                "      \"cityName\": \"杭州市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3305\",\n" +
                "      \"cityName\": \"湖州市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3401\",\n" +
                "      \"cityName\": \"合肥市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3404\",\n" +
                "      \"cityName\": \"淮南市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3406\",\n" +
                "      \"cityName\": \"淮北市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3410\",\n" +
                "      \"cityName\": \"黄山市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3416\",\n" +
                "      \"cityName\": \"亳州市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3717\",\n" +
                "      \"cityName\": \"菏泽市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4106\",\n" +
                "      \"cityName\": \"鹤壁市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4202\",\n" +
                "      \"cityName\": \"黄石市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4211\",\n" +
                "      \"cityName\": \"黄冈市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4304\",\n" +
                "      \"cityName\": \"衡阳市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4312\",\n" +
                "      \"cityName\": \"怀化市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4413\",\n" +
                "      \"cityName\": \"惠州市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4416\",\n" +
                "      \"cityName\": \"河源市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4511\",\n" +
                "      \"cityName\": \"贺州市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4512\",\n" +
                "      \"cityName\": \"河池市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4601\",\n" +
                "      \"cityName\": \"海口市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5325\",\n" +
                "      \"cityName\": \"红河哈尼族彝族自治州\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6107\",\n" +
                "      \"cityName\": \"汉中市\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6321\",\n" +
                "      \"cityName\": \"海东地区\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6322\",\n" +
                "      \"cityName\": \"海北藏族自治州\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6323\",\n" +
                "      \"cityName\": \"黄南藏族自治州\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6325\",\n" +
                "      \"cityName\": \"海南藏族自治州\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6328\",\n" +
                "      \"cityName\": \"海西蒙古族藏族自治州\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6522\",\n" +
                "      \"cityName\": \"哈密地区\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6532\",\n" +
                "      \"cityName\": \"和田地区\",\n" +
                "      \"cityFirst\": \"H\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1405\",\n" +
                "      \"cityName\": \"晋城市\",\n" +
                "      \"cityFirst\": \"J\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1407\",\n" +
                "      \"cityName\": \"晋中市\",\n" +
                "      \"cityFirst\": \"J\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2107\",\n" +
                "      \"cityName\": \"锦州市\",\n" +
                "      \"cityFirst\": \"J\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2202\",\n" +
                "      \"cityName\": \"吉林市\",\n" +
                "      \"cityFirst\": \"J\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2303\",\n" +
                "      \"cityName\": \"鸡西市\",\n" +
                "      \"cityFirst\": \"J\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2308\",\n" +
                "      \"cityName\": \"佳木斯市\",\n" +
                "      \"cityFirst\": \"J\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3304\",\n" +
                "      \"cityName\": \"嘉兴市\",\n" +
                "      \"cityFirst\": \"J\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3307\",\n" +
                "      \"cityName\": \"金华市\",\n" +
                "      \"cityFirst\": \"J\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3602\",\n" +
                "      \"cityName\": \"景德镇市\",\n" +
                "      \"cityFirst\": \"J\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3604\",\n" +
                "      \"cityName\": \"九江市\",\n" +
                "      \"cityFirst\": \"J\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3608\",\n" +
                "      \"cityName\": \"吉安市\",\n" +
                "      \"cityFirst\": \"J\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3701\",\n" +
                "      \"cityName\": \"济南市\",\n" +
                "      \"cityFirst\": \"J\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3708\",\n" +
                "      \"cityName\": \"济宁市\",\n" +
                "      \"cityFirst\": \"J\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4108\",\n" +
                "      \"cityName\": \"焦作市\",\n" +
                "      \"cityFirst\": \"J\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4208\",\n" +
                "      \"cityName\": \"荆门市\",\n" +
                "      \"cityFirst\": \"J\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4210\",\n" +
                "      \"cityName\": \"荆州市\",\n" +
                "      \"cityFirst\": \"J\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4407\",\n" +
                "      \"cityName\": \"江门市\",\n" +
                "      \"cityFirst\": \"J\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4452\",\n" +
                "      \"cityName\": \"揭阳市\",\n" +
                "      \"cityFirst\": \"J\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6202\",\n" +
                "      \"cityName\": \"嘉峪关市\",\n" +
                "      \"cityFirst\": \"J\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6203\",\n" +
                "      \"cityName\": \"金昌市\",\n" +
                "      \"cityFirst\": \"J\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6209\",\n" +
                "      \"cityName\": \"酒泉市\",\n" +
                "      \"cityFirst\": \"J\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4102\",\n" +
                "      \"cityName\": \"开封市\",\n" +
                "      \"cityFirst\": \"K\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5301\",\n" +
                "      \"cityName\": \"昆明市\",\n" +
                "      \"cityFirst\": \"K\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6502\",\n" +
                "      \"cityName\": \"克拉玛依市\",\n" +
                "      \"cityFirst\": \"K\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6530\",\n" +
                "      \"cityName\": \"克孜勒苏柯尔克孜自治州\",\n" +
                "      \"cityFirst\": \"K\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6531\",\n" +
                "      \"cityName\": \"喀什地区\",\n" +
                "      \"cityFirst\": \"K\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1310\",\n" +
                "      \"cityName\": \"廊坊市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1410\",\n" +
                "      \"cityName\": \"临汾市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1411\",\n" +
                "      \"cityName\": \"吕梁市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2110\",\n" +
                "      \"cityName\": \"辽阳市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2204\",\n" +
                "      \"cityName\": \"辽源市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3207\",\n" +
                "      \"cityName\": \"连云港市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3311\",\n" +
                "      \"cityName\": \"丽水市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3312\",\n" +
                "      \"cityName\": \"临安市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3415\",\n" +
                "      \"cityName\": \"六安市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3508\",\n" +
                "      \"cityName\": \"龙岩市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3712\",\n" +
                "      \"cityName\": \"莱芜市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3713\",\n" +
                "      \"cityName\": \"临沂市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3715\",\n" +
                "      \"cityName\": \"聊城市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4103\",\n" +
                "      \"cityName\": \"洛阳市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4111\",\n" +
                "      \"cityName\": \"漯河市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4313\",\n" +
                "      \"cityName\": \"娄底市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4502\",\n" +
                "      \"cityName\": \"柳州市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4513\",\n" +
                "      \"cityName\": \"来宾市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4614\",\n" +
                "      \"cityName\": \"临高县\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4617\",\n" +
                "      \"cityName\": \"乐东县\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4618\",\n" +
                "      \"cityName\": \"陵水县\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5105\",\n" +
                "      \"cityName\": \"泸州市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5111\",\n" +
                "      \"cityName\": \"乐山市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5134\",\n" +
                "      \"cityName\": \"凉山彝族自治州\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5202\",\n" +
                "      \"cityName\": \"六盘水市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5307\",\n" +
                "      \"cityName\": \"丽江市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5309\",\n" +
                "      \"cityName\": \"临沧市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5401\",\n" +
                "      \"cityName\": \"拉萨市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5426\",\n" +
                "      \"cityName\": \"林芝地区\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6201\",\n" +
                "      \"cityName\": \"兰州市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6212\",\n" +
                "      \"cityName\": \"陇南市\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6229\",\n" +
                "      \"cityName\": \"临夏回族自治州\",\n" +
                "      \"cityFirst\": \"L\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2310\",\n" +
                "      \"cityName\": \"牡丹江市\",\n" +
                "      \"cityFirst\": \"M\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3405\",\n" +
                "      \"cityName\": \"马鞍山市\",\n" +
                "      \"cityFirst\": \"M\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4409\",\n" +
                "      \"cityName\": \"茂名市\",\n" +
                "      \"cityFirst\": \"M\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4414\",\n" +
                "      \"cityName\": \"梅州市\",\n" +
                "      \"cityFirst\": \"M\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5107\",\n" +
                "      \"cityName\": \"绵阳市\",\n" +
                "      \"cityFirst\": \"M\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5114\",\n" +
                "      \"cityName\": \"眉山市\",\n" +
                "      \"cityFirst\": \"M\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3201\",\n" +
                "      \"cityName\": \"南京市\",\n" +
                "      \"cityFirst\": \"N\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3206\",\n" +
                "      \"cityName\": \"南通市\",\n" +
                "      \"cityFirst\": \"N\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3302\",\n" +
                "      \"cityName\": \"宁波市\",\n" +
                "      \"cityFirst\": \"N\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3507\",\n" +
                "      \"cityName\": \"南平市\",\n" +
                "      \"cityFirst\": \"N\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3509\",\n" +
                "      \"cityName\": \"宁德市\",\n" +
                "      \"cityFirst\": \"N\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3601\",\n" +
                "      \"cityName\": \"南昌市\",\n" +
                "      \"cityFirst\": \"N\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4113\",\n" +
                "      \"cityName\": \"南阳市\",\n" +
                "      \"cityFirst\": \"N\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4501\",\n" +
                "      \"cityName\": \"南宁市\",\n" +
                "      \"cityFirst\": \"N\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5110\",\n" +
                "      \"cityName\": \"内江市\",\n" +
                "      \"cityFirst\": \"N\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5113\",\n" +
                "      \"cityName\": \"南充市\",\n" +
                "      \"cityFirst\": \"N\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5333\",\n" +
                "      \"cityName\": \"怒江傈僳族自治州\",\n" +
                "      \"cityFirst\": \"N\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5424\",\n" +
                "      \"cityName\": \"那曲地区\",\n" +
                "      \"cityFirst\": \"N\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2111\",\n" +
                "      \"cityName\": \"盘锦市\",\n" +
                "      \"cityFirst\": \"P\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3503\",\n" +
                "      \"cityName\": \"莆田市\",\n" +
                "      \"cityFirst\": \"P\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3603\",\n" +
                "      \"cityName\": \"萍乡市\",\n" +
                "      \"cityFirst\": \"P\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4104\",\n" +
                "      \"cityName\": \"平顶山市\",\n" +
                "      \"cityFirst\": \"P\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4109\",\n" +
                "      \"cityName\": \"濮阳市\",\n" +
                "      \"cityFirst\": \"P\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5104\",\n" +
                "      \"cityName\": \"攀枝花市\",\n" +
                "      \"cityFirst\": \"P\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6208\",\n" +
                "      \"cityName\": \"平凉市\",\n" +
                "      \"cityFirst\": \"P\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1303\",\n" +
                "      \"cityName\": \"秦皇岛市\",\n" +
                "      \"cityFirst\": \"Q\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2302\",\n" +
                "      \"cityName\": \"齐齐哈尔市\",\n" +
                "      \"cityFirst\": \"Q\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2309\",\n" +
                "      \"cityName\": \"七台河市\",\n" +
                "      \"cityFirst\": \"Q\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3308\",\n" +
                "      \"cityName\": \"衢州市\",\n" +
                "      \"cityFirst\": \"Q\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3505\",\n" +
                "      \"cityName\": \"泉州市\",\n" +
                "      \"cityFirst\": \"Q\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3702\",\n" +
                "      \"cityName\": \"青岛市\",\n" +
                "      \"cityFirst\": \"Q\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4418\",\n" +
                "      \"cityName\": \"清远市\",\n" +
                "      \"cityFirst\": \"Q\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4507\",\n" +
                "      \"cityName\": \"钦州市\",\n" +
                "      \"cityFirst\": \"Q\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4607\",\n" +
                "      \"cityName\": \"琼海市\",\n" +
                "      \"cityFirst\": \"Q\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4620\",\n" +
                "      \"cityName\": \"琼中县\",\n" +
                "      \"cityFirst\": \"Q\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5223\",\n" +
                "      \"cityName\": \"黔西南布依族苗族自治州\",\n" +
                "      \"cityFirst\": \"Q\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5226\",\n" +
                "      \"cityName\": \"黔东南苗族侗族自治州\",\n" +
                "      \"cityFirst\": \"Q\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5227\",\n" +
                "      \"cityName\": \"黔南布依族苗族自治州\",\n" +
                "      \"cityFirst\": \"Q\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5303\",\n" +
                "      \"cityName\": \"曲靖市\",\n" +
                "      \"cityFirst\": \"Q\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6210\",\n" +
                "      \"cityName\": \"庆阳市\",\n" +
                "      \"cityFirst\": \"Q\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3711\",\n" +
                "      \"cityName\": \"日照市\",\n" +
                "      \"cityFirst\": \"R\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5423\",\n" +
                "      \"cityName\": \"日喀则地区\",\n" +
                "      \"cityFirst\": \"R\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1301\",\n" +
                "      \"cityName\": \"石家庄市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1406\",\n" +
                "      \"cityName\": \"朔州市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2101\",\n" +
                "      \"cityName\": \"沈阳市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2113\",\n" +
                "      \"cityName\": \"朝阳市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2203\",\n" +
                "      \"cityName\": \"四平市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2207\",\n" +
                "      \"cityName\": \"松原市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2305\",\n" +
                "      \"cityName\": \"双鸭山市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2312\",\n" +
                "      \"cityName\": \"绥化市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3100\",\n" +
                "      \"cityName\": \"上海市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3205\",\n" +
                "      \"cityName\": \"苏州市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3213\",\n" +
                "      \"cityName\": \"宿迁市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3306\",\n" +
                "      \"cityName\": \"绍兴市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3413\",\n" +
                "      \"cityName\": \"宿州市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3504\",\n" +
                "      \"cityName\": \"三明市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3611\",\n" +
                "      \"cityName\": \"上饶市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4112\",\n" +
                "      \"cityName\": \"三门峡市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4114\",\n" +
                "      \"cityName\": \"商丘市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4203\",\n" +
                "      \"cityName\": \"十堰市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4213\",\n" +
                "      \"cityName\": \"随州市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4305\",\n" +
                "      \"cityName\": \"邵阳市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4402\",\n" +
                "      \"cityName\": \"韶关市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4403\",\n" +
                "      \"cityName\": \"深圳市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4405\",\n" +
                "      \"cityName\": \"汕头市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4415\",\n" +
                "      \"cityName\": \"汕尾市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4602\",\n" +
                "      \"cityName\": \"三亚市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5109\",\n" +
                "      \"cityName\": \"遂宁市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5306\",\n" +
                "      \"cityName\": \"昭通市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5308\",\n" +
                "      \"cityName\": \"思茅市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5422\",\n" +
                "      \"cityName\": \"山南地区\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6110\",\n" +
                "      \"cityName\": \"商洛市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6402\",\n" +
                "      \"cityName\": \"石嘴山市\",\n" +
                "      \"cityFirst\": \"S\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1200\",\n" +
                "      \"cityName\": \"天津市\",\n" +
                "      \"cityFirst\": \"T\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1302\",\n" +
                "      \"cityName\": \"唐山市\",\n" +
                "      \"cityFirst\": \"T\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1401\",\n" +
                "      \"cityName\": \"太原市\",\n" +
                "      \"cityFirst\": \"T\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1505\",\n" +
                "      \"cityName\": \"通辽市\",\n" +
                "      \"cityFirst\": \"T\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2112\",\n" +
                "      \"cityName\": \"铁岭市\",\n" +
                "      \"cityFirst\": \"T\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2205\",\n" +
                "      \"cityName\": \"通化市\",\n" +
                "      \"cityFirst\": \"T\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3212\",\n" +
                "      \"cityName\": \"泰州市\",\n" +
                "      \"cityFirst\": \"T\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3310\",\n" +
                "      \"cityName\": \"台州市\",\n" +
                "      \"cityFirst\": \"T\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3407\",\n" +
                "      \"cityName\": \"铜陵市\",\n" +
                "      \"cityFirst\": \"T\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3709\",\n" +
                "      \"cityName\": \"泰安市\",\n" +
                "      \"cityFirst\": \"T\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4232\",\n" +
                "      \"cityName\": \"天门市\",\n" +
                "      \"cityFirst\": \"T\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4612\",\n" +
                "      \"cityName\": \"屯昌县\",\n" +
                "      \"cityFirst\": \"T\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5222\",\n" +
                "      \"cityName\": \"铜仁地区\",\n" +
                "      \"cityFirst\": \"T\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6102\",\n" +
                "      \"cityName\": \"铜川市\",\n" +
                "      \"cityFirst\": \"T\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6205\",\n" +
                "      \"cityName\": \"天水市\",\n" +
                "      \"cityFirst\": \"T\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6521\",\n" +
                "      \"cityName\": \"吐鲁番地区\",\n" +
                "      \"cityFirst\": \"T\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6542\",\n" +
                "      \"cityName\": \"塔城地区\",\n" +
                "      \"cityFirst\": \"T\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1503\",\n" +
                "      \"cityName\": \"乌海市\",\n" +
                "      \"cityFirst\": \"W\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1509\",\n" +
                "      \"cityName\": \"乌兰察布市\",\n" +
                "      \"cityFirst\": \"W\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3202\",\n" +
                "      \"cityName\": \"无锡市\",\n" +
                "      \"cityFirst\": \"W\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3303\",\n" +
                "      \"cityName\": \"温州市\",\n" +
                "      \"cityFirst\": \"W\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3402\",\n" +
                "      \"cityName\": \"芜湖市\",\n" +
                "      \"cityFirst\": \"W\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3707\",\n" +
                "      \"cityName\": \"潍坊市\",\n" +
                "      \"cityFirst\": \"W\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3710\",\n" +
                "      \"cityName\": \"威海市\",\n" +
                "      \"cityFirst\": \"W\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4201\",\n" +
                "      \"cityName\": \"武汉市\",\n" +
                "      \"cityFirst\": \"W\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4504\",\n" +
                "      \"cityName\": \"梧州市\",\n" +
                "      \"cityFirst\": \"W\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4605\",\n" +
                "      \"cityName\": \"五指山市\",\n" +
                "      \"cityFirst\": \"W\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4606\",\n" +
                "      \"cityName\": \"文昌市\",\n" +
                "      \"cityFirst\": \"W\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4608\",\n" +
                "      \"cityName\": \"万宁市\",\n" +
                "      \"cityFirst\": \"W\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5326\",\n" +
                "      \"cityName\": \"文山壮族苗族自治州\",\n" +
                "      \"cityFirst\": \"W\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6105\",\n" +
                "      \"cityName\": \"渭南市\",\n" +
                "      \"cityFirst\": \"W\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6206\",\n" +
                "      \"cityName\": \"武威市\",\n" +
                "      \"cityFirst\": \"W\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6403\",\n" +
                "      \"cityName\": \"吴忠市\",\n" +
                "      \"cityFirst\": \"W\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6501\",\n" +
                "      \"cityName\": \"乌鲁木齐市\",\n" +
                "      \"cityFirst\": \"W\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1305\",\n" +
                "      \"cityName\": \"邢台市\",\n" +
                "      \"cityFirst\": \"X\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1409\",\n" +
                "      \"cityName\": \"忻州市\",\n" +
                "      \"cityFirst\": \"X\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1522\",\n" +
                "      \"cityName\": \"兴安盟\",\n" +
                "      \"cityFirst\": \"X\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1525\",\n" +
                "      \"cityName\": \"锡林郭勒盟\",\n" +
                "      \"cityFirst\": \"X\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3203\",\n" +
                "      \"cityName\": \"徐州市\",\n" +
                "      \"cityFirst\": \"X\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3418\",\n" +
                "      \"cityName\": \"宣城市\",\n" +
                "      \"cityFirst\": \"X\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3502\",\n" +
                "      \"cityName\": \"厦门市\",\n" +
                "      \"cityFirst\": \"X\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3605\",\n" +
                "      \"cityName\": \"新余市\",\n" +
                "      \"cityFirst\": \"X\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4107\",\n" +
                "      \"cityName\": \"新乡市\",\n" +
                "      \"cityFirst\": \"X\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4110\",\n" +
                "      \"cityName\": \"许昌市\",\n" +
                "      \"cityFirst\": \"X\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4115\",\n" +
                "      \"cityName\": \"信阳市\",\n" +
                "      \"cityFirst\": \"X\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4206\",\n" +
                "      \"cityName\": \"襄樊市\",\n" +
                "      \"cityFirst\": \"X\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4209\",\n" +
                "      \"cityName\": \"孝感市\",\n" +
                "      \"cityFirst\": \"X\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4212\",\n" +
                "      \"cityName\": \"咸宁市\",\n" +
                "      \"cityFirst\": \"X\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4230\",\n" +
                "      \"cityName\": \"仙桃市\",\n" +
                "      \"cityFirst\": \"X\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4303\",\n" +
                "      \"cityName\": \"湘潭市\",\n" +
                "      \"cityFirst\": \"X\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4331\",\n" +
                "      \"cityName\": \"湘西土家族苗族自治州\",\n" +
                "      \"cityFirst\": \"X\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5328\",\n" +
                "      \"cityName\": \"西双版纳傣族自治州\",\n" +
                "      \"cityFirst\": \"X\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6101\",\n" +
                "      \"cityName\": \"西安市\",\n" +
                "      \"cityFirst\": \"X\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6104\",\n" +
                "      \"cityName\": \"咸阳市\",\n" +
                "      \"cityFirst\": \"X\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6301\",\n" +
                "      \"cityName\": \"西宁市\",\n" +
                "      \"cityFirst\": \"X\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1403\",\n" +
                "      \"cityName\": \"阳泉市\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1408\",\n" +
                "      \"cityName\": \"运城市\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2108\",\n" +
                "      \"cityName\": \"营口市\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2224\",\n" +
                "      \"cityName\": \"延边朝鲜族自治州\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"2307\",\n" +
                "      \"cityName\": \"伊春市\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3209\",\n" +
                "      \"cityName\": \"盐城市\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3210\",\n" +
                "      \"cityName\": \"扬州市\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3606\",\n" +
                "      \"cityName\": \"鹰潭市\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3609\",\n" +
                "      \"cityName\": \"宜春市\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3706\",\n" +
                "      \"cityName\": \"烟台市\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4205\",\n" +
                "      \"cityName\": \"宜昌市\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4306\",\n" +
                "      \"cityName\": \"岳阳市\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4309\",\n" +
                "      \"cityName\": \"益阳市\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4311\",\n" +
                "      \"cityName\": \"永州市\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4417\",\n" +
                "      \"cityName\": \"阳江市\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4453\",\n" +
                "      \"cityName\": \"云浮市\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4509\",\n" +
                "      \"cityName\": \"玉林市\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4621\",\n" +
                "      \"cityName\": \"洋浦开发区\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5115\",\n" +
                "      \"cityName\": \"宜宾市\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5118\",\n" +
                "      \"cityName\": \"雅安市\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5304\",\n" +
                "      \"cityName\": \"玉溪市\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6106\",\n" +
                "      \"cityName\": \"延安市\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6108\",\n" +
                "      \"cityName\": \"榆林市\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6327\",\n" +
                "      \"cityName\": \"玉树藏族自治州\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6401\",\n" +
                "      \"cityName\": \"银川市\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6540\",\n" +
                "      \"cityName\": \"伊犁哈萨克自治州\",\n" +
                "      \"cityFirst\": \"Y\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"1307\",\n" +
                "      \"cityName\": \"张家口市\",\n" +
                "      \"cityFirst\": \"Z\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3211\",\n" +
                "      \"cityName\": \"镇江市\",\n" +
                "      \"cityFirst\": \"Z\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3309\",\n" +
                "      \"cityName\": \"舟山市\",\n" +
                "      \"cityFirst\": \"Z\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3506\",\n" +
                "      \"cityName\": \"漳州市\",\n" +
                "      \"cityFirst\": \"Z\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3703\",\n" +
                "      \"cityName\": \"淄博市\",\n" +
                "      \"cityFirst\": \"Z\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"3704\",\n" +
                "      \"cityName\": \"枣庄市\",\n" +
                "      \"cityFirst\": \"Z\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4101\",\n" +
                "      \"cityName\": \"郑州市\",\n" +
                "      \"cityFirst\": \"Z\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4116\",\n" +
                "      \"cityName\": \"周口市\",\n" +
                "      \"cityFirst\": \"Z\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4117\",\n" +
                "      \"cityName\": \"驻马店市\",\n" +
                "      \"cityFirst\": \"Z\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4302\",\n" +
                "      \"cityName\": \"株洲市\",\n" +
                "      \"cityFirst\": \"Z\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4308\",\n" +
                "      \"cityName\": \"张家界市\",\n" +
                "      \"cityFirst\": \"Z\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4404\",\n" +
                "      \"cityName\": \"珠海市\",\n" +
                "      \"cityFirst\": \"Z\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4408\",\n" +
                "      \"cityName\": \"湛江市\",\n" +
                "      \"cityFirst\": \"Z\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4412\",\n" +
                "      \"cityName\": \"肇庆市\",\n" +
                "      \"cityFirst\": \"Z\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"4420\",\n" +
                "      \"cityName\": \"中山市\",\n" +
                "      \"cityFirst\": \"Z\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5103\",\n" +
                "      \"cityName\": \"自贡市\",\n" +
                "      \"cityFirst\": \"Z\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5120\",\n" +
                "      \"cityName\": \"资阳市\",\n" +
                "      \"cityFirst\": \"Z\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"5203\",\n" +
                "      \"cityName\": \"遵义市\",\n" +
                "      \"cityFirst\": \"Z\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6207\",\n" +
                "      \"cityName\": \"张掖市\",\n" +
                "      \"cityFirst\": \"Z\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cityID\": \"6405\",\n" +
                "      \"cityName\": \"中卫市\",\n" +
                "      \"cityFirst\": \"Z\"\n" +
                "    }\n" +
                "  ]";
    }
}
