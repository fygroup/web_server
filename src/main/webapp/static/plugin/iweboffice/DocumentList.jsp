<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.*,java.sql.*,java.text.SimpleDateFormat,java.text.DateFormat,java.util.Date,com.jeeplus.modules.oa.web.iDBManager2000.*" %>
<%!
  com.jeeplus.modules.oa.web.iDBManager2000 DbaObj=new com.jeeplus.modules.oa.web.iDBManager2000();
//�г�����ģ��
public String GetTemplateList(String ObjType, String FileType)
  {
    String mTemplateList,mstr="";
    mTemplateList = "<select name=" + ObjType + " >";
    mTemplateList = mTemplateList +  "<option value=''>--------����ģ��--------</option>";
    String Sql = "select RecordID,Descript from Template_File where FileType='" + FileType + "'"; //�����ݿ�
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
   * ���ܻ����ã���ʽ������ʱ��
   * @param DateValue �������ڻ�ʱ��
   * @param DateType ��ʽ�� EEEE������, yyyy����, MM����, dd����, HH��Сʱ, mm�Ƿ���,  ss����
   * @return ����ַ���
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
<title>���Ƽ�-iWebOffice2003�����ĵ�ʵ��</title>
<link rel='stylesheet' type='text/css' href='test.css'>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<body bgcolor="#ffffff">
<div align="center"><font size=4 color=ff0000>�칫�Զ��������ĵ�����ʾ��[iWebOffice2003Ƕ���]</font></div>
<br>
<table width="100%"><tr><td height="22"><script src="iWebOffice2003.js"></script></td></tr></table>
<hr size=1>

<table border=0  cellspacing='0' cellpadding='0' width=100% align=center class=TBStyle>
<tr>
  <td rowspan="2" nowrap class="TDTitleStyle" style="line-height:20px; padding-left:10px;">
    <p style="color:#000000">����ע�⣺<br>
      ����ʽ���ʵ�ָ�ǿ��Ĺ��ܣ���Ҫ��ʽ��������Ƽ���ϵ��0791-82221588����<br>
      �����ϣ����ȷ��ʾ��ʾ��������Ҫ��������������<br>
      ��1����ʹ��Windows XP���ϲ���ϵͳ��OFFICE2003���ϵı༭�����IE6���ϵ��������<br>
      ��2�����ڴ򿪱�ҳ�浯����װ����Ĵ���ʱѡ�񡾰�װ����ť������������װiWebOffice2003�����<br>
      ��3��������������Զ���װiWebOffice2003���������������<a href="InstallClient.zip">���ذ�װ����</a>��<br>
	  </p>
    <p>��ǰ�༭�û���
      <input type=text name=username size=8 value="��ʾ��">
      �����ã��༭����ʱ��ͬ�û������ۼ���ɫ�᲻ͬ���������޸��ĵ�ǰ�������ݡ�<br> 
        </p></td>
  <td class="TDTitleStyle" align="center" width="550">
    &nbsp;<input type=button name="BookMark" value="��ǩ����"  onclick="javascript:location.href='BookMark/BookMarkList.jsp'">
     &nbsp;<input type=button name="Template" value="ģ�����"  onclick="javascript:location.href='Template/TemplateList.jsp'">
     &nbsp;<input type=button name="Template" value="ǩ�¹���"  onclick="javascript:location.href='Signature/SignatureList.jsp'">&nbsp;  </td>
</tr>
<tr>
  <td colspan=4 class="TDTitleStyle" align="center" style="line-height:28px;">
    ��ѡ��ģ�棺<%=GetTemplateList("doc",".doc")%>
    <input type=button value="�½�word�ĵ�  "  onclick="javascript:location.href='DocumentEdit.jsp?FileType=.doc&UserName='+username.value+'&Template='+doc.value;">
    <br>
    ��ѡ��ģ�棺<%=GetTemplateList("xls",".xls")%>
    <input type=button value="�½�excel�ĵ� "  onclick="javascript:location.href='DocumentEdit.jsp?FileType=.xls&UserName='+username.value+'&Template='+xls.value;">
    <br>
    ��ѡ��ģ�棺<%=GetTemplateList("wps",".wps")%>
    <input type=button value="�½�wps�ĵ�   "  onclick="javascript:location.href='DocumentEdit.jsp?FileType=.wps&UserName='+username.value+'&Template='+wps.value;">
    <br>
    ��ѡ��ģ�棺<%=GetTemplateList("et",".et")%>
    <input type=button value="�½���ɽ���  "  onclick="javascript:location.href='DocumentEdit.jsp?FileType=.et&UserName='+username.value+'&Template='+et.value;">
  </td>
</tr>
</table>
<br>

<table border=0  cellspacing='0' cellpadding='0' width=100% align=center class=TBStyle>
<tr>
  <td nowrap align=center class="TDTitleStyle" width="80" height="25">���</td>
  <td nowrap align=center class="TDTitleStyle">����</td>
  <td nowrap align=center class="TDTitleStyle" width="100">����</td>
  <td nowrap align=center class="TDTitleStyle" width="100">����</td>
  <td nowrap align=center class="TDTitleStyle" width="100">����</td>
  <td nowrap align=center class="TDTitleStyle" width="480">����</td>
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
    <input type=button onclick="javascript:location.href='DocumentEdit.jsp?RecordID=<%=RecordID%>&EditType=0&UserName='+username.value;" value="�Ķ�">
    <input type=button onclick="javascript:location.href='DocumentEdit.jsp?RecordID=<%=RecordID%>&EditType=1&UserName='+username.value;" value="�޸�[�޺ۼ�]">
    <input type=button onclick="javascript:location.href='DocumentEdit.jsp?RecordID=<%=RecordID%>&EditType=2&UserName='+username.value;" value="�޸�[�кۼ�]">
    <input type=button onclick="javascript:location.href='DocumentEdit.jsp?RecordID=<%=RecordID%>&EditType=3&UserName='+username.value;" value="���">
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