//1
var calendar = echarts.init(document.getElementById('calendar'), 'macarons');
//2
calendar.showLoading();

var data = getVirtulData(2018);
function getVirtulData(year) {
    year = year || '2018';
    var date = +echarts.number.parseDate(year + '-01-01');
    var end = +echarts.number.parseDate((+year + 1) + '-01-01');
    var dayTime = 3600 * 24 * 1000;
    var data = [];
    for (
        var time = date; time < end; time += dayTime) {
        data.push([
            echarts.format.formatTime('yyyy-MM-dd', time),
            Math.floor(Math.random() * 10000)
        ]);
    }
    return data;
}

//
option = {
    // backgroundColor: '#334B5C',
    title: {
        top: 30,
        text: '2018年某站点全年XXX的数据',
        subtext: '我是子标题',
        left: 'center',
        textStyle: {
            //color: '#ddb926'
        }
    },
    tooltip: {
        trigger: 'item'
    },
    calendar: [{
        top: 100,
        left: 'center',
        range: ['2018-01-01', '2018-12-31'],
        splitLine: {
            show: true,
            lineStyle: {
                // color: '#000',
                // 外边框
                // width: 2,
                // type: 'solid'
            }
        },
        yearLabel: {
            formatter: '{start}  1st',
            textStyle: {
                // color: '#ddb926'
            }
        },
        itemStyle: {
            normal: {
                //color: '#334B5C',
                borderWidth: 1,
                //borderColor: '#111'
            }
        }
    }],
    series: [
        {
            name: '步数',
            type: 'scatter',
            coordinateSystem: 'calendar',
            data: data,
            symbolSize: function (val) {
                return val[1] / 500;
            },
            itemStyle: {
                normal: {
                    //color: '#ddb926'
                }
            }
        },
        {
            name: '步数',
            type: 'scatter',
            coordinateSystem: 'calendar',
            calendarIndex: 1,
            data: data,
            symbolSize: function (val) {
                return val[1] / 500;
            },
            itemStyle: {
                normal: {
                    //color: '#ddb926'
                }
            }
        },
        {
            name: 'Top 12',
            type: 'effectScatter',
            coordinateSystem: 'calendar',
            calendarIndex: 1,
            data: data.sort(function (a, b) {
                return b[1] - a[1];
            }).slice(0, 12),
            symbolSize: function (val) {
                return val[1] / 500;
            },
            showEffectOn: 'render',
            rippleEffect: {
                brushType: 'stroke'
            },
            hoverAnimation: true,
            itemStyle: {
                normal: {
                    //color: '#f4e925',
                    shadowBlur: 10,
                    shadowColor: '#333'
                }
            },
            zlevel: 1
        },
        {
            name: 'Top 12',
            type: 'effectScatter',
            coordinateSystem: 'calendar',
            data: data.sort(function (a, b) {
                return b[1] - a[1];
            }).slice(0, 12),
            symbolSize: function (val) {
                return val[1] / 500;
            },
            showEffectOn: 'render',
            rippleEffect: {
                brushType: 'stroke'
            },
            hoverAnimation: true,
            itemStyle: {
                normal: {
                    //color: '#f4e925',
                    shadowBlur: 10,
                    shadowColor: '#333'
                }
            },
            zlevel: 1
        }
    ]
};
//3
calendar.hideLoading();
//4
calendar.setOption(option);