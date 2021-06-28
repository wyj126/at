export {
    getButtonData,sortButtonData,getThisPageButtonStyle,getPageBtnHtmlAndData
}

/**
 * 获取当前用户按钮数据
 */
function getButtonData() {
    $.ajaxSetup({async:false});
    let currentUserData;
    $.get("/roleResource/currentUserBtn",function (data) {
        if(data.state == "ok"){
            currentUserData = data.obj;
            sessionStorage.setItem("currentUserBtnData",JSON.stringify(currentUserData));
        }else{
            layer.msg("用户按钮数据获取失败");
        }
    })
    return currentUserData;
}

/**
 * 按钮分类
 * @param thisPageButtonData 当前页面按钮数据
 * @param thisPageName 当前页面管理对象
 * @param isEnableUpdate 仅适用于资源管理页面 如果isEnableUpdate为禁止更新 且拥有编辑权限 此时 编辑按钮不展示
 */
function sortButtonData(thisPageButtonData,thisPageName,isEnableUpdate) {
    const headButtonData = ["添加"+thisPageName,"批量删除","删除数据展示","搜索","刷新列表","永久删除"];
    const tableButtonData = ["删除","编辑","是否启用","是否启用更新","恢复"];
    let currentButtonData = {};
    let currentHeadButtonData = [];
    let currentTableButtonData = [];
    for(let index in thisPageButtonData){
        if(headButtonData.includes(thisPageButtonData[index])){
            currentHeadButtonData.push(thisPageButtonData[index]);
        }
        if(tableButtonData.includes(thisPageButtonData[index])){
            if(thisPageButtonData[index] == "编辑"&&isEnableUpdate == "禁止更新") continue;
            currentTableButtonData.push(thisPageButtonData[index]);
        }
    }
    currentButtonData.currentHeadButtonData = currentHeadButtonData;
    currentButtonData.currentTableButtonData = currentTableButtonData;
    return currentButtonData;
}

/**
 * 获取对应页面的按钮数据和对应的html
 * @param pathName 当前页面路径
 * @param thisPageName
 * @param isEnable,isEnableUpdate 仅适用于资源管理页面
 * @returns {{}}
 */
function getPageBtnHtmlAndData(pathName,thisPageName,isEnable,isEnableUpdate) {
    let currentUserBtnData = sessionStorage.getItem("currentUserBtnData");
    let currentUserBtnJsonData;
    if(currentUserBtnData == null || currentUserBtnData == undefined || currentUserBtnData == ""){
        currentUserBtnJsonData = getButtonData();
    }else{
        currentUserBtnJsonData = JSON.parse(currentUserBtnData);
    }
    const thisPageButtonData = currentUserBtnJsonData[pathName];
    let sortButton = sortButtonData(thisPageButtonData,thisPageName,isEnableUpdate);
    const thisPageHeadBtnData = sortButton.currentHeadButtonData;
    const thisPageTableBtnData = sortButton.currentTableButtonData;
    let thisPageHeadBtnHtml = "";
    let thisPageTableBtnHtml = "";
    for(const i in thisPageHeadBtnData){
        thisPageHeadBtnHtml += getThisPageButtonStyle(thisPageHeadBtnData[i],thisPageName,isEnable,isEnableUpdate);
    }
    for(const j in thisPageTableBtnData){
        thisPageTableBtnHtml += getThisPageButtonStyle(thisPageTableBtnData[j],thisPageName,isEnable,isEnableUpdate);
    }
    let thisPageBtnHtmlAndData = {};
    thisPageBtnHtmlAndData.thisPageHeadBtnHtml = thisPageHeadBtnHtml;
    thisPageBtnHtmlAndData.thisPageTableBtnHtml = thisPageTableBtnHtml;
    thisPageBtnHtmlAndData.currentHeadButtonData = thisPageHeadBtnData;
    thisPageBtnHtmlAndData.currentTableButtonData = thisPageTableBtnData;
    return thisPageBtnHtmlAndData;
}


/**
 * 获取相应按钮样式
 * @param buttonName 按钮名称
 * @param thisPageName
 * @param isEnable,isEnableUpdate 仅适用于资源管理页面
 * @returns {string}
 */
function getThisPageButtonStyle(buttonName,thisPageName,isEnable,isEnableUpdate) {
    switch (buttonName) {
        case "删除数据展示":{
            return "<div class='layui-inline' style='margin-right: 5px'>"+
                        "<a class='layui-btn layui-btn-danger layui-btn-normal showDelAll_btn'>删除数据展示</a>"+
                    "</div>";
        }
        case "添加"+thisPageName:{
            return "<div class='layui-inline' style='margin-right: 5px'>"+
                        "<a class='layui-btn layui-btn-normal add_btn'>添加"+thisPageName+"</a>"+
                    "</div>";
        }
        case "删除":{
            return 	"<a class='layui-btn layui-btn-xs layui-btn-danger' lay-event='del'>删除</a>";
        }
        case "编辑":{
            return "<a class='layui-btn layui-btn-xs' lay-event='edit'>编辑</a>";
        }
        case "批量删除":{
            return "<div class='layui-inline' style='margin-right: 5px'>"+
                        "<a class='layui-btn layui-btn-danger layui-btn-normal delAll_btn'>批量删除</a>"+
                    "</div>";
        }
        case "是否启用更新":{
            return "<a class='layui-btn layui-btn-xs layui-btn-warm' lay-event='update'>"+isEnableUpdate+"</a>";
        }
        case "是否启用":{
            return "<a class='layui-btn layui-btn-xs layui-btn-warm' lay-event='usable'>"+isEnable+"</a>";
        }
        case "搜索":{
            return "<div class='layui-inline' style='margin-right: 5px'>"+
                        "<a class='layui-btn layui-btn-normal search_btn'>搜索</a>"+
                    "</div>";
        }
        case "刷新列表":{
            return "<div class='layui-inline' style='margin-right: 5px'>"+
                        "<a class='layui-btn layui-btn-normal reloadTable'>刷新列表</a>"+
                    "</div>";
        }
        case "永久删除":{
            return "<div class='layui-inline' style='margin-right: 5px'>"+
                        "<a class='layui-btn layui-btn-danger layui-btn-normal delAllForever_btn'>永久删除</a>"+
                    "</div>";
        }
        case "恢复":{
            return "<a class='layui-btn layui-btn-xs layui-btn-warm' lay-event='recover'>恢复</a>";
        }
    }
}
