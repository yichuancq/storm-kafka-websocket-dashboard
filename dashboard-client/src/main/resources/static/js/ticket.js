//1
var time_line = echarts.init(document.getElementById('timeTicket'), 'macarons');
//2
time_line.showLoading();
//
// 异步获取数据
var socket = io.connect(location.protocol + '//' + document.domain + ':' + 8081);
socket.on('event_memory', function (data) {
    time_line.hideLoading();
    fill_time_line_data(data);
});

//
function fill_time_line_data(data) {
    var num = data.data;
    var time_line_option = {
        title: {
            text: '实时内存占用推送',
            subtext: '实时内存',
            //left: 'center'
        },
        color: [
            'rgba(255, 69, 0, 0.5)',
            'rgba(255, 150, 0, 0.5)',
            'rgba(255, 200, 0, 0.5)',
            'rgba(155, 200, 50, 0.5)',
            'rgba(55, 200, 100, 0.5)'
        ],
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c}%"
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        series: [{
            name: '业务指标',
            type: 'gauge',
            center: ['50%', '40%'],
            // center: ['25%', '55%'],
            splitNumber: 10,       // 分割段数，默认为5
            axisLine: {            // 坐标轴线
                lineStyle: {       // 属性lineStyle控制线条样式
                    // color: [[0.2, '#228b22'],[0.8, '#48b'],[1, '#ff4500']],
                    width: 8
                }
            },
            axisTick: {            // 坐标轴小标记
                splitNumber: 10,   // 每份split细分多少段
                length: 12,        // 属性length控制线长
                lineStyle: {       // 属性lineStyle控制线条样式
                    color: 'auto'
                }
            },
            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    color: 'auto'
                }
            },
            splitLine: {           // 分隔线
                show: true,        // 默认显示，属性show控制显示与否
                length: 30,         // 属性length控制线长
                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                    color: 'auto'
                }
            },
            pointer: {
                width: 5
            },
            title: {
                show: true,
                offsetCenter: [0, '-40%'],       // x, y，单位px
                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    fontWeight: 'bolder'
                }
            },
            detail: {
                formatter: '{value}%',
                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    color: 'auto',
                    fontWeight: 'bolder'
                }
            },
            // 仪表盘数据
            data: [{value: num, name: '内存使用率'}]
        }
        ]
    };
    //
    time_line.setOption(time_line_option);// 设置地图参数
}

//4
//4
