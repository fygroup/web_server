<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.*,java.sql.*,java.text.SimpleDateFormat,java.text.DateFormat,java.util.Date,com.jeeplus.modules.oa.web.iDBManager2000.*" %>
<%!
  com.jeeplus.modules.oa.web.iDBManager2000 DbaObj=new com.jeeplus.modules.oa.web.iDBManager2000();
//列出所有模版
public String GetTemplateList(String ObjType, String FileType)
  {
    String mTemplateList,mstr="";
    mTemplateList = "<select name=" + ObjType + " >";
    mTemplateList = mTemplateList +  "<option value=''>--------不用模版--------</option>";
    String Sql = "select RecordID,Descript from Template_File where FileType='" + FileType + "'"; //打开数据库
    try {
      if (DbaObj.OpenConnection()) {
        try {
          ResultSet result = DbaObj.ExecuteQuery(Sql);
          mstr="selected";
          while (result.next()) {
            mTemplateList = mTemplateList + "<option value='" + result.getString("RecordID") + "'"+mstr+">" + result.getString("Descript") + "</option>";
          }
          result.close();
        }
        catch (SQLException sqlex) {
          System.out.println(sqlex.toString());
        }
      }
      else {
        System.out.println("GetTemplateList: OpenDatabase Error");
      }
    }
    finally {
      DbaObj.CloseConnection();
    }
    mTemplateList = mTemplateList + "</select>";
    return (mTemplateList);
  }
  /**
   * 功能或作用：格式化日期时间
   * @param DateValue 输入日期或时间
   * @param DateType 格式化 EEEE是星期, yyyy是年, MM是月, dd是日, HH是小时, mm是分钟,  ss是秒
   * @return 输出字符串
   */
  public String FormatDate(String DateValue,String DateType)
  {
    String Result;
    SimpleDateFormat formatter = new SimpleDateFormat(DateType);
    try{
      Date mDateTime = formatter.parse(DateValue);
      Result = formatter.format(mDateTime);
    }catch(Exception ex){
      Result = ex.getMessage();
    }
    if (Result.equalsIgnoreCase("1900-01-01")){
      Result = "";
    }
    return Result;
  }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>金格科技-iWebOffice2003网络文档实例</title>
<link rel='stylesheet' type='text/css' href='test.css'>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<body bgcolor="#ffffff">
<div align="center"><font size=4 color=ff0000>办公自动化网络文档处理示例[iWebOffice2003嵌入版]</font></div>
<br>
<table width="100%"><tr><td height="22"><script src="iWebOffice2003.js"></script></td></tr></table>
<hr size=1>

<table border=0  cellspacing='0' cellpadding='0' width=100% align=center class=TBStyle>
<tr>
  <td rowspan="2" nowrap class="TDTitleStyle" style="line-height:20px; padding-left:10px;">
    <p style="color:#000000">请您注意：<br>
      　正式版可实现更强大的功能，需要正式版请与金格科技联系（0791-82221588）。<br>
      　如果希望正确演示本示例，您需要符合下列条件：<br>
      　1、请使用Windows XP以上操作系统、OFFICE2003以上的编辑软件、IE6以上的浏览器。<br>
      　2、请在打开本页面弹出安装插件的窗口时选择【安装】按钮，才能正常安装iWebOffice2003插件。<br>
      　3、如果不能正常自动安装iWebOffice2003插件，请你在这里<a href="InstallClient.zip">下载安装程序</a>。<br>
	  </p>
    <p>当前编辑用户：
      <input type=text name=username size=8 value="演示人">
      ，作用：编辑留痕时不同用户保留痕迹颜色会不同，所以在修改文档前请更改身份。<br> 
        </p></td>
  <td class="TDTitleStyle" align="center" width="550">
    &nbsp;<input type=button name="BookMark" value="标签管理"  onclick="javascript:location.href='BookMark/BookMarkList.jsp'">
     &nbsp;<input type=button name="Template" value="模板管理"  onclick="javascript:location.href='Template/TemplateList.jsp'">
     &nbsp;<input type=button name="Template" value="签章管理"  onclick="javascript:location.href='Signature/SignatureList.jsp'">&nbsp;  </td>
</tr>
<tr>
  <td colspan=4 class="TDTitleStyle" align="center" style="line-height:28px;">
    请选择模版：<%=GetTemplateList("doc",".doc")%>
    <input type=button value="新建word文档  "  onclick="javascript:location.href='DocumentEdit.jsp?FileType=.doc&UserName='+username.value+'&Template='+doc.value;">
    <br>
    请选择模版：<%=GetTemplateList("xls",".xls")%>
    <input type=button value="新建excel文档 "  onclick="javascript:location.href='DocumentEdit.jsp?FileType=.xls&UserName='+username.value+'&Template='+xls.value;">
    <br>
    请选择模版：<%=GetTemplateList("wps",".wps")%>
    <input type=button value="新建wps文档   "  onclick="javascript:location.href='DocumentEdit.jsp?FileType=.wps&UserName='+username.value+'&Template='+wps.value;">
    <br>
    请选择模版：<%=GetTemplateList("et",".et")%>
    <input type=button value="新建金山表格  "  onclick="javascript:location.href='DocumentEdit.jsp?FileType=.et&UserName='+username.value+'&Template='+et.value;">
  </td>
</tr>
</table>
<br>

<table border=0  cellspacing='0' cellpadding='0' width=100% align=center class=TBStyle>
<tr>
  <td nowrap align=center class="TDTitleStyle" width="80" height="25">编号</td>
  <td nowrap align=center class="TDTitleStyle">主题</td>
  <td nowrap align=center class="TDTitleStyle" width="100">作者</td>
  <td nowrap align=center class="TDTitleStyle" width="100">类型</td>
  <td nowrap align=center class="TDTitleStyle" width="100">日期</td>
  <td nowrap align=center class="TDTitleStyle" width="480">操作</td>
</tr>
<%
    try {
      if (DbaObj.OpenConnection()) {
        try {
          ResultSet result = DbaObj.ExecuteQuery("select Status,RecordID,HtmlPath,DocumentID,Subject,Author,FileType,FileDate from Document order by DocumentID desc limit 0,32");
          while (result.next()) {
            String RecordID = result.getString("RecordID");
            String HTMLPath = result.getString("HtmlPath");
            if (HTMLPath == null)
               HTMLPath = "";
%>
<tr>
  <td nowrap align=center class="TDStyle"><%=result.getString("DocumentID")%></td>
  <td align=center class="TDStyle"><%=result.getString("Subject")%></td>
  <td align=center class="TDStyle"><%=result.getString("Author")%></td>
  <td nowrap align=center class="TDStyle"><%=result.getString("FileType")%></td>
  <td align=center class="TDStyle"><%=FormatDate(result.getString("FileDate"),"yyyy-MM-dd HH:mm:ss")%></td>
  <td nowrap align=center class="TDStyle">
    <input type=button onclick="javascript:location.href='DocumentEdit.jsp?RecordID=<%=RecordID%>&EditType=0&UserName='+username.value;" value="阅读">
    <input type=button onclick="javascript:location.href='DocumentEdit.jsp?RecordID=<%=RecordID%>&EditType=1&UserName='+username.value;" value="修改[无痕迹]">
    <input type=button onclick="javascript:location.href='DocumentEdit.jsp?RecordID=<%=RecordID%>&EditType=2&UserName='+username.value;" value="修改[有痕迹]">
    <input type=button onclick="javascript:location.href='DocumentEdit.jsp?RecordID=<%=RecordID%>&EditType=3&UserName='+username.value;" value="审核">
    <input type=button <% if (HTMLPath.equalsIgnoreCase("")) out.write("disabled"); %> onclick="javascript:window.open('<%=HTMLPath%>');" value="HTML">
  </td>
</tr>
<%
          }
          result.close();
        }
        catch (SQLException sqlex) {
          System.out.println(sqlex.toString());
        }
      }
      else {
        out.println("OpenDatabase Error");
      }
    }
    finally {
      DbaObj.CloseConnection();
    }
%>
</table>
</body>
</html>