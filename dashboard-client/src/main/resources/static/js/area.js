//地图容器
// 对应html页面的id
var map_1 = echarts.init(document.getElementById('china2'), 'macarons');
map_1.showLoading();
var geoCoordMap = {};
var data2 = [];
//初始化echarts实例
//使用制定的配置项和数据显示图表
$.getJSON('js/map.json', function (data) {
    $.each(data, function (infoIndex, info) {
        var city = info.children;
        //
        for (var i = 0; i < city.length; i++) {
            var citydetail = new Array();
            var name = city[i].name;
            geoCoordMap[name] = citydetail;
            var lat = parseFloat(city[i].lat);
            var log = parseFloat(city[i].log);
            citydetail.push(log);
            citydetail.push(lat);
        }
    });
});
// 异步获取数据
var socket = io.connect(location.protocol + '//' + document.domain + ':' + 8081);
socket.on('event_map', function (data) {
    // console.info("" + data);
    map_1.hideLoading();
    fillData(data);
});

socket.on('reconnect_failed', function () {
    console("Reconnect Failed");
});// end animation
// 动态填充数据
function fillData(data) {
    data2 = data.specialMap;
    map_1_option.series[0].data = convertData(data2.sort(function (a, b) {
        return b.value - a.value;
    })), map_1.setOption(map_1_option);// 设置地图参数

}

var convertData = function (data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
        var geoCoord = geoCoordMap[data[i].name];
        if (geoCoord) {
            res.push({
                name: data[i].name,
                value: geoCoord.concat(data[i].value)
            });
        }
    }
    return res;
};
//地图容器
//34个省、市、自治区的名字拼音映射数组
//网络零售当期分布
var map_1_option = {
    // backgroundColor: '#334B5C',
    title: {
        text: '全国主要物流XXX分布',
        subtext: 'demo subtext',
        left: 'center',
        //标题颜色
        textStyle: {
            // color: '#fff'
        }
    },
    toolbox: {
        show: true,
        orient: 'vertical',
        left: 'right', top: 'center',
        feature: {
            dataView: {readOnly: false},
            restore: {}, saveAsImage: {}
        }
    },
    tooltip: {
        trigger: 'item'
    },
    //图例
    legend: {
        orient: 'vertical',
        y: 'bottom',
        x: 'left',
        data: ['pm2.5'],
        textStyle: {
            color: '#334B5C'
        }
    },
    geo: {
        map: 'china',
        label: {
            emphasis: {
                show: false
            }
        },
        roam: false,
        itemStyle: {
            normal: {
                // 背景颜色
                // areaColor: '#6AB0B8',
                borderColor: '#111',
            },
            emphasis: {
                // 高亮状态下的样式
                areaColor: '#d1d1d1'
            }
        }
    },

    series: [
        {
            name: 'Top 5',
            type: 'effectScatter',
            coordinateSystem: 'geo',
            symbolSize: function (val) {
                return val[2] / 10;
            },
            showEffectOn: 'render',
            rippleEffect: {
                brushType: 'stroke'
            },
            hoverAnimation: true,
            //显示标签
            label: {
                normal: {
                    formatter: '{b}',
                    position: 'right',
                    show: true,
                    // color: '#fff'
                },
                emphasis: {
                    // color: '#333'
                }
            },
            itemStyle: {
                normal: {
                    // color: 'red',
                    shadowBlur: 20,
                    shadowColor: '#000'
                }
            },
            zlevel: 1
        }
    ]
};