//
// home()

// 启动快手极速版
app.launchApp('快手极速版');

console.log('Quick Hand Script Start')
console.log("device.width:", device.width)
console.log("device.height:", device.height)

quick_home();

quick_money();
swipe(device.width / 2, 500, device.width / 2, device.width / 2, device.height)


// 
// click("去点赞", 0)

// 回到首页
// home() 

// 退出脚本
// exits()

/**
 * 快手首页
 */
function quick_home() {
    click("首页", 0)
}

/**
 * 去赚钱
 */
function quick_money() {
    click("去赚钱", 0)
}