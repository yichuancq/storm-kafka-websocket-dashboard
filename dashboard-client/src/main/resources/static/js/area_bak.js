//地图容器
// 对应html页面的id
var map_1 = echarts.init(document.getElementById('china2'));
var geoCoordMap = {};
var data2 = [];
//初始化echarts实例
//使用制定的配置项和数据显示图表
$.getJSON('js/map.json', function (data) {
    $.each(data, function (infoIndex, info) {
        var city = info.children;
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
socket.on('pushpoint', function (data) {
    // console.info("" + data);
    fillData(data);
});

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

    title: {
        text: '全国主要物流XXX分布',
        subtext: 'demo subtext',
        left: 'center',
        textStyle: {
            color: '#fff'
        }
    },
    tooltip: {
        trigger: 'item'
    },

    //图例
    legend: {
        orient: 'vertical',
        y: 'bottom',
        x: 'right',
        data: ['pm2.5'],
        textStyle: {
            color: '#fff'
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
                areaColor: '#6AB0B8',
                // areaColor: '#d1d1d1',
                borderColor: '#111',
            },
            emphasis: {
                // 高亮状态下的样式
                areaColor: '#d1d1d1'
            }
        }
    },

    series: [
        /*{
            name: 'pm2.5',
            type: 'scatter',
            coordinateSystem: 'geo',
            data: convertData(data2),
            symbolSize: function (val) {
                return val[2] / 10;
            },
            label: {
                normal: {
                    formatter: '{b}',
                    position: 'right',
                    show: false
                },
                emphasis: {
                    show: true
                }
            },
            itemStyle: {
                normal: {
                    color: '#f75749'
                }
            }
         },*/
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
                    color: '#fff'
                },
                emphasis: {
                    color: '#333'
                }
            },
            itemStyle: {
                normal: {
                    //地图坐标颜色
                    color: '#f75749',
                    // color: '#f75749',
                    shadowBlur: 10,
                    shadowColor: '#333'
                }
            },
            zlevel: 1
        }
    ]
};


