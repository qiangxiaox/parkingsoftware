



function initReport(){
    var reportData = "";
    $.ajax({
        type:'get',
        async:false,
        url:"/accounts/income/getData",
        success:function (data) {
            reportData = data;
        }
    })
    
    
    //定义一个js变量 用来接收echarts配置对象
    option = {
        title: {//标题  整个报表的主标题
            text: '本月收支报表'
        },
        tooltip: {//tip 提示  提示工具
            trigger: 'axis'
        },
        legend: {//单个元素示例
            data:['收入','支出']
        },
        grid: {//设置报表在容器中的相当位置
            left: '3%',//距离左侧3%
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {//工具项
            feature: {
                saveAsImage: {}//以图片的形式保存当前报表
            }
        },
        xAxis: {//X轴(横轴)显示的数据
            type: 'category',//代表当前轴的类型 value为数值轴  category 类目轴
                             //除数字之外 其余情况全部使用类目轴
            boundaryGap: false,
            data: reportData.statData
        },
        yAxis: {
            type: 'value'//数值轴
        },
        series: [//报表中所需要展示数据
            {
                name:'收入',
                type:'line',//当前报表类型 line折线 bar代表柱状 pie饼图
               // stack: '总量',//把每条线的数据求和
                data:reportData.incomeData//当前需要展示的Y轴数据
            },
            {
                name:'支出',
                type:'line',
                //stack: '总量',
                data:reportData.expenditureData
            }
        ]
    };

    var myChart = echarts.init(document.getElementById('accounts'));
    myChart.setOption(option);
}
