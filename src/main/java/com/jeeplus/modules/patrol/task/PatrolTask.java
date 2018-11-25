package com.jeeplus.modules.patrol.task;



import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.*;

import com.jeeplus.modules.cpu.service.CpuService;

import com.jeeplus.modules.exception.entity.exception.ResourceException;
import com.jeeplus.modules.exception.service.exception.ResourceExceptionService;
import com.jeeplus.modules.memory.entity.MemoryUsedRate;
import com.jeeplus.modules.memory.service.MemoryService;
import com.jeeplus.modules.patrolDocument.entity.PatrolDocument;
import com.jeeplus.modules.patrol.entity.Task;
import com.jeeplus.modules.patrolDocument.service.PatrolDocumentService;
import com.jeeplus.modules.resource.entity.AvailabilityRate;
import com.jeeplus.modules.resource.entity.HealthDegree;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resource.entity.ResponseTime;
import com.jeeplus.modules.resource.service.ResourceService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.quartz.DisallowConcurrentExecution;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static com.jeeplus.modules.resource.web.ResourceController.formatTime;


/**
 * 智能巡检-服务器、网络设备、安全设备
 * @author   huanglei
 * @version 2018/11/12
 * @email 616754909@qq.com
 */
@DisallowConcurrentExecution
public class PatrolTask extends Task {

	private ResourceService resourceService = (ResourceService)SpringBeanUtils.getBeanByClass(ResourceService.class);

	private PatrolDocumentService patrolDocumentService= (PatrolDocumentService)SpringBeanUtils.getBeanByClass(PatrolDocumentService.class);

	private CpuService cpuService= (CpuService)SpringBeanUtils.getBeanByClass(CpuService.class);

	private MemoryService memoryService= (MemoryService)SpringBeanUtils.getBeanByClass(MemoryService.class);

	private ResourceExceptionService resourceExceptionService = (ResourceExceptionService)SpringBeanUtils.getBeanByClass(ResourceExceptionService.class);


	@Override
	public void run(String i,String j,String patrolId) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("--------------------------------任务在 "+dateFormat.format(new Date())+" 时运行"+"------------------------------------------------------------");
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");

		patrolResource(i,j,patrolId);

		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("---------------------------------------------------------------任务结束---------------------------------------------------------------");
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------\n\n\n\n");

	}
	void patrolResource(String i,String j,String patrolId) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmsssss");
		Long star = System.currentTimeMillis();
		Date date = new Date();
		String nameDateFormat = fmt.format(date);
//		try {
//			String title = "";//根据设备类型设置标题为设备名
//			if(i.equals("0")){
//				title = "服务器";
//				resource.setSelectResourceTypeCodeIds("2");
//			}else if(i.equals("1")){
//				title = "网络设备";
//				resource.setSelectResourceTypeCodeIds("1");
//			}else if(i.equals("2")){
//				title = "链路";
//				resource.setSelectResourceTypeCodeIds("3");
//			}else{
//				title = "安全设备";
//				resource.setSelectResourceTypeCodeIds("6");
//			}
//
        //服务器
        if(i.equals("0")){
		try {
			Long start =System.currentTimeMillis();
			String title = "服务器";
			Resource resource=new Resource();
			resource.setSelectResourceTypeCodeIds("2");
			List<Resource> list = resourceService.findList(resource);

			String[] head = {};

			if(j.equals("0,")){
				head = new String[]{"服务器名称", "管理IP", "平均健康度", "可用率", "非健康时长", "非健康起始时间", "异常等级",
						"异常项", "ICMP响应时间"};
			}else if(j.equals("1,")){
				head = new String[]{"服务器名称", "管理IP", "平均健康度", "可用率", "非健康时长", "非健康起始时间", "异常等级",
						"异常项", "CPU利用率"};
			}else if (j.equals("2,")){
				head = new String[]{"服务器名称", "管理IP", "平均健康度", "可用率", "非健康时长", "非健康起始时间", "异常等级",
						"异常项", "MEN利用率"};
			}else if(j.equals("0,1,")) {
				head = new String[]{"服务器名称", "管理IP", "平均健康度", "可用率", "非健康时长", "非健康起始时间", "异常等级",
						"异常项", "ICMP响应时间","CPU利用率"};
			}else if(j.equals("0,2,")) {
				head = new String[]{"服务器名称", "管理IP", "平均健康度", "可用率", "非健康时长", "非健康起始时间", "异常等级",
						"异常项", "ICMP响应时间","MEN利用率"};
			} else if(j.equals("1,2,")) {
				head = new String[]{"服务器名称", "管理IP", "平均健康度", "可用率", "非健康时长", "非健康起始时间", "异常等级",
						"异常项", "CPU利用率","MEN利用率"};
			}else if(j.equals("0,1,2,")) {
				head = new String[]{"服务器名称", "管理IP", "平均健康度", "可用率", "非健康时长", "非健康起始时间", "异常等级",
						"异常项", "ICMP响应时间","CPU利用率","MEN利用率"};
			}
			HSSFWorkbook workbook = new HSSFWorkbook(); //产生工作簿对象
			HSSFSheet sheet = workbook.createSheet("服务器"); //产生工作表对象
			setSheetStyle(sheet);
			// 设置单元格类型
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			setHeadStyle(cellStyle, workbook);

			HSSFCellStyle cellStyleRed = workbook.createCellStyle();
			setHeadStyleRed(cellStyleRed, workbook);

			HSSFCellStyle cellStyleYellow = workbook.createCellStyle();
			setHeadStyleYellow(cellStyleYellow, workbook);

			//产生一行
			HSSFRow row1 = sheet.createRow((short) 0);
			//添加表头
			//添加表头
			for (int k = 0; k < head.length; k++) {
				Cell cell1 = row1.createCell(k);
				cell1.setCellValue(head[k]);
				cell1.setCellStyle(cellStyle);
			}

			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			DecimalFormat df = new DecimalFormat("######0.00");

			for (int k = 0; k < list.size(); k++) {
				row1 = sheet.createRow(k + 1);
				Resource r = list.get(k);
				Cell cellName =row1.createCell(0);
				cellName.setCellValue(r.getName());

				row1.createCell(1).setCellValue(r.getIp());
				List<HealthDegree> healthDegreeList=resourceService.findHealthDegreeList(r.getId());
				Double healthDegreeTotal=0.0;
				for(HealthDegree h:healthDegreeList){
					healthDegreeTotal+=Transformation.null2Double(h.getHealthDegree());
				}

				List<AvailabilityRate> availabilityRateList=resourceService.findAvailabilityRateList(r.getId());

				Double availabilityRateTotal=0.0;
				for(AvailabilityRate h:availabilityRateList){
					availabilityRateTotal+=Transformation.null2Double(h.getAvailabilityRate());
				}

				if(healthDegreeList.size()>0){
					row1.createCell(2).setCellValue(df.format(Transformation.null2Double(healthDegreeTotal)/healthDegreeList.size()));
				}else{
					row1.createCell(2).setCellValue("-");
				}


				if(healthDegreeList.size()>0){
					row1.createCell(3).setCellValue(df.format(Transformation.null2Double(availabilityRateTotal)/availabilityRateList.size()));
				}else{
					row1.createCell(3).setCellValue("-");
				}
				String unHealthStartTime=null;
				Long unHealthTotalTime=0L;
				Long time=0L;
				for(HealthDegree h:healthDegreeList){
					if(Transformation.null2Double(h.getHealthDegree())<100.00){
						if(unHealthStartTime==null){
							unHealthStartTime=sdf.format(h.getCreateDate());
						}
						//unHealth=true;
						if(time>0L){
							unHealthTotalTime+=h.getCreateDate().getTime()-time;
							time=0l;
						}
						time=h.getCreateDate().getTime();
					}else{
						if(time>0L){
							unHealthTotalTime+=h.getCreateDate().getTime()-time;
							time=0l;
						}
					}
				}

				if(time>0L){
					unHealthTotalTime+=System.currentTimeMillis()-time;
					time=0l;
				}
				row1.createCell(4).setCellValue(formatTime(unHealthTotalTime));
				row1.createCell(5).setCellValue(unHealthStartTime);

				List<ResourceException> exceptionList=resourceExceptionService.findEListByResource(r.getId(),null);
				if(exceptionList == null || exceptionList.size()==0){
					row1.createCell(6).setCellValue("正常");
					row1.createCell(7).setCellValue("-");

				}else {
					row1.createCell(6).setCellValue(exceptionList.get(0).getExceptionClass());
					row1.createCell(7).setCellValue(exceptionList.get(0).getIndicatorName());
				}
                //通断-ICMP响应时间
				if(j.equals("0,")){
					Cell responseTime  = row1.createCell(8);
					ResponseTime icmpTime = resourceService.getTopResponseTime(r.getId());
					responseTime.setCellValue(icmpTime.getTime()+"ms");
                 //CPU利用率
				}else if(j.equals("1,")){
					Cell cpu = row1.createCell(8);
					String usedRate=cpuService.findCpuUsedRateNew(r.getId());
					if(usedRate==null || usedRate ==""){
						usedRate="0.00";
					}
					cpu.setCellValue(usedRate+"%");

				//MEM利用率
				}else if (j.equals("2,")){
					Cell memory = row1.createCell(8);
					MemoryUsedRate memoryUsedRate= memoryService.getTopMemoryUsedRate(r.getId());
					memory.setCellValue(memoryUsedRate.getUsedRate()+"%");
                 //通断-ICMP响应时间 CPU利用率
				}else if(j.equals("0,1,")) {
					Cell responseTime  = row1.createCell(8);
					ResponseTime icmpTime = resourceService.getTopResponseTime(r.getId());
					responseTime.setCellValue(icmpTime.getTime()+"ms");
					Cell cpu = row1.createCell(9);
					String usedRate=cpuService.findCpuUsedRateNew(r.getId());
					if(usedRate==null || usedRate ==""){
						usedRate="0.00";
					}
					cpu.setCellValue(usedRate+"%");
				//通断-ICMP响应时间 	MEM利用率
				}else if(j.equals("0,2,")) {
					Cell responseTime  = row1.createCell(8);
					ResponseTime icmpTime = resourceService.getTopResponseTime(r.getId());
					responseTime.setCellValue(icmpTime.getTime()+"ms");
					Cell memory = row1.createCell(9);
					MemoryUsedRate memoryUsedRate= memoryService.getTopMemoryUsedRate(r.getId());
					memory.setCellValue(memoryUsedRate.getUsedRate()+"%");
				//CPU利用率 	MEM利用率
				} else if(j.equals("1,2,")) {
					Cell cpu = row1.createCell(8);
					String usedRate=cpuService.findCpuUsedRateNew(r.getId());
					if(usedRate==null || usedRate ==""){
						usedRate="0.00";
					}
					cpu.setCellValue(usedRate+"%");
					Cell memory = row1.createCell(9);
					MemoryUsedRate memoryUsedRate= memoryService.getTopMemoryUsedRate(r.getId());
					memory.setCellValue(memoryUsedRate.getUsedRate()+"%");
				//通断-ICMP响应时间   CPU利用率 	MEM利用率
				}else if(j.equals("0,1,2,")) {
					Cell responseTime  = row1.createCell(8);
					ResponseTime icmpTime = resourceService.getTopResponseTime(r.getId());
					responseTime.setCellValue(icmpTime.getTime()+"ms");
					Cell cpu = row1.createCell(9);
					String usedRate=cpuService.findCpuUsedRateNew(r.getId());
					if(usedRate==null || usedRate ==""){
						usedRate="0.00";
					}
					cpu.setCellValue(usedRate+"%");
					Cell memory = row1.createCell(10);
					MemoryUsedRate memoryUsedRate= memoryService.getTopMemoryUsedRate(r.getId());
					memory.setCellValue(memoryUsedRate.getUsedRate()+"%");

				}
				FileOutputStream outputStream;
				File directory = new File("");//参数为空
				String courseFile = directory.getCanonicalPath()+"//src//main//webapp//userfiles//";
				courseFile= courseFile.replaceAll("\\\\", "//");//
				outputStream = new FileOutputStream(courseFile+nameDateFormat+"服务器.xls");
				workbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				//定义新对象-巡检文件表


			}
			PatrolDocument patrolDocument = new PatrolDocument();
			patrolDocument.setName(nameDateFormat+"服务器");//文件名称
			patrolDocument.setType("xls");//文件类型
			patrolDocument.setPatrolId(patrolId);//巡检关联id
			patrolDocument.setDocumentUrls("/web/userfiles/"+nameDateFormat+"服务器.xls");
			patrolDocumentService.save(patrolDocument);//保存
		} catch (Exception e) {
			e.printStackTrace();
		}

         //网络设备
		}else if(i.equals("1")){
			try {
				Long start =System.currentTimeMillis();
				String title = "网络设备";
				Resource resource=new Resource();
				resource.setSelectResourceTypeCodeIds("1");
				List<Resource> list = resourceService.findList(resource);

				String[] head = {};

				if(j.equals("0,")){
					head = new String[]{"网络设备名称", "管理IP", "平均健康度", "可用率", "非健康时长", "非健康起始时间", "异常等级",
							"异常项", "ICMP响应时间"};
				}else if(j.equals("1,")){
					head = new String[]{"网络设备名称", "管理IP", "平均健康度", "可用率", "非健康时长", "非健康起始时间", "异常等级",
							"异常项", "CPU利用率"};
				}else if (j.equals("2,")){
					head = new String[]{"网络设备名称", "管理IP", "平均健康度", "可用率", "非健康时长", "非健康起始时间", "异常等级",
							"异常项", "MEN利用率"};
				}else if(j.equals("0,1,")) {
					head = new String[]{"网络设备名称", "管理IP", "平均健康度", "可用率", "非健康时长", "非健康起始时间", "异常等级",
							"异常项", "ICMP响应时间","CPU利用率"};
				}else if(j.equals("0,2,")) {
					head = new String[]{"网络设备名称", "管理IP", "平均健康度", "可用率", "非健康时长", "非健康起始时间", "异常等级",
							"异常项", "ICMP响应时间","MEN利用率"};
				} else if(j.equals("1,2,")) {
					head = new String[]{"网络设备名称", "管理IP", "平均健康度", "可用率", "非健康时长", "非健康起始时间", "异常等级",
							"异常项", "CPU利用率","MEN利用率"};
				}else if(j.equals("0,1,2,")) {
					head = new String[]{"网络设备名称", "管理IP", "平均健康度", "可用率", "非健康时长", "非健康起始时间", "异常等级",
							"异常项", "ICMP响应时间","CPU利用率","MEN利用率"};
				}
				HSSFWorkbook workbook = new HSSFWorkbook(); //产生工作簿对象
				HSSFSheet sheet = workbook.createSheet("网络设备名称"); //产生工作表对象
				setSheetStyle(sheet);
				// 设置单元格类型
				HSSFCellStyle cellStyle = workbook.createCellStyle();
				setHeadStyle(cellStyle, workbook);

				HSSFCellStyle cellStyleRed = workbook.createCellStyle();
				setHeadStyleRed(cellStyleRed, workbook);

				HSSFCellStyle cellStyleYellow = workbook.createCellStyle();
				setHeadStyleYellow(cellStyleYellow, workbook);

				//产生一行
				HSSFRow row1 = sheet.createRow((short) 0);
				//添加表头
				//添加表头
				for (int k = 0; k < head.length; k++) {
					Cell cell1 = row1.createCell(k);
					cell1.setCellValue(head[k]);
					cell1.setCellStyle(cellStyle);
				}

				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				DecimalFormat df = new DecimalFormat("######0.00");

				for (int k = 0; k < list.size(); k++) {
					row1 = sheet.createRow(k + 1);
					Resource r = list.get(k);
					Cell cellName =row1.createCell(0);
					cellName.setCellValue(r.getName());

					row1.createCell(1).setCellValue(r.getIp());
					List<HealthDegree> healthDegreeList=resourceService.findHealthDegreeList(r.getId());
					Double healthDegreeTotal=0.0;
					for(HealthDegree h:healthDegreeList){
						healthDegreeTotal+=Transformation.null2Double(h.getHealthDegree());
					}

					List<AvailabilityRate> availabilityRateList=resourceService.findAvailabilityRateList(r.getId());

					Double availabilityRateTotal=0.0;
					for(AvailabilityRate h:availabilityRateList){
						availabilityRateTotal+=Transformation.null2Double(h.getAvailabilityRate());
					}

					if(healthDegreeList.size()>0){
						row1.createCell(2).setCellValue(df.format(Transformation.null2Double(healthDegreeTotal)/healthDegreeList.size()));
					}else{
						row1.createCell(2).setCellValue("-");
					}


					if(healthDegreeList.size()>0){
						row1.createCell(3).setCellValue(df.format(Transformation.null2Double(availabilityRateTotal)/availabilityRateList.size()));
					}else{
						row1.createCell(3).setCellValue("-");
					}
					String unHealthStartTime=null;
					Long unHealthTotalTime=0L;
					Long time=0L;
					for(HealthDegree h:healthDegreeList){
						if(Transformation.null2Double(h.getHealthDegree())<100.00){
							if(unHealthStartTime==null){
								unHealthStartTime=sdf.format(h.getCreateDate());
							}
							//unHealth=true;
							if(time>0L){
								unHealthTotalTime+=h.getCreateDate().getTime()-time;
								time=0l;
							}
							time=h.getCreateDate().getTime();
						}else{
							if(time>0L){
								unHealthTotalTime+=h.getCreateDate().getTime()-time;
								time=0l;
							}
						}
					}

					if(time>0L){
						unHealthTotalTime+=System.currentTimeMillis()-time;
						time=0l;
					}
					row1.createCell(4).setCellValue(formatTime(unHealthTotalTime));
					row1.createCell(5).setCellValue(unHealthStartTime);

					List<ResourceException> exceptionList=resourceExceptionService.findEListByResource(r.getId(),null);
					if(exceptionList == null || exceptionList.size()==0){
						row1.createCell(6).setCellValue("正常");
						row1.createCell(7).setCellValue("-");

					}else {
						row1.createCell(6).setCellValue(exceptionList.get(0).getExceptionClass());
						row1.createCell(7).setCellValue(exceptionList.get(0).getIndicatorName());
					}
					//通断-ICMP响应时间
					if(j.equals("0,")){
						Cell responseTime  = row1.createCell(8);
						ResponseTime icmpTime = resourceService.getTopResponseTime(r.getId());
						responseTime.setCellValue(icmpTime.getTime()+"ms");
						//CPU利用率
					}else if(j.equals("1,")){
						Cell cpu = row1.createCell(8);
						String usedRate=cpuService.findCpuUsedRateNew(r.getId());
						if(usedRate==null || usedRate ==""){
							usedRate="0.00";
						}
						cpu.setCellValue(usedRate+"%");

						//MEM利用率
					}else if (j.equals("2,")){
						Cell memory = row1.createCell(8);
						MemoryUsedRate memoryUsedRate= memoryService.getTopMemoryUsedRate(r.getId());
						memory.setCellValue(memoryUsedRate.getUsedRate()+"%");
						//通断-ICMP响应时间 CPU利用率
					}else if(j.equals("0,1,")) {
						Cell responseTime  = row1.createCell(8);
						ResponseTime icmpTime = resourceService.getTopResponseTime(r.getId());
						responseTime.setCellValue(icmpTime.getTime()+"ms");
						Cell cpu = row1.createCell(9);
						String usedRate=cpuService.findCpuUsedRateNew(r.getId());
						if(usedRate==null || usedRate ==""){
							usedRate="0.00";
						}
						cpu.setCellValue(usedRate+"%");
						//通断-ICMP响应时间 	MEM利用率
					}else if(j.equals("0,2,")) {
						Cell responseTime  = row1.createCell(8);
						ResponseTime icmpTime = resourceService.getTopResponseTime(r.getId());
						responseTime.setCellValue(icmpTime.getTime()+"ms");
						Cell memory = row1.createCell(9);
						MemoryUsedRate memoryUsedRate= memoryService.getTopMemoryUsedRate(r.getId());
						memory.setCellValue(memoryUsedRate.getUsedRate()+"%");
						//CPU利用率 	MEM利用率
					} else if(j.equals("1,2,")) {
						Cell cpu = row1.createCell(8);
						String usedRate=cpuService.findCpuUsedRateNew(r.getId());
						if(usedRate==null || usedRate ==""){
							usedRate="0.00";
						}
						cpu.setCellValue(usedRate+"%");
						Cell memory = row1.createCell(9);
						MemoryUsedRate memoryUsedRate= memoryService.getTopMemoryUsedRate(r.getId());
						memory.setCellValue(memoryUsedRate.getUsedRate()+"%");
						//通断-ICMP响应时间   CPU利用率 	MEM利用率
					}else if(j.equals("0,1,2,")) {
						Cell responseTime  = row1.createCell(8);
						ResponseTime icmpTime = resourceService.getTopResponseTime(r.getId());
						responseTime.setCellValue(icmpTime.getTime()+"ms");
						Cell cpu = row1.createCell(9);
						String usedRate=cpuService.findCpuUsedRateNew(r.getId());
						if(usedRate==null || usedRate ==""){
							usedRate="0.00";
						}
						cpu.setCellValue(usedRate+"%");
						Cell memory = row1.createCell(10);
						MemoryUsedRate memoryUsedRate= memoryService.getTopMemoryUsedRate(r.getId());
						memory.setCellValue(memoryUsedRate.getUsedRate()+"%");

					}
					FileOutputStream outputStream;
					File directory = new File("");//参数为空
					String courseFile = directory.getCanonicalPath()+"//src//main//webapp//userfiles//";
					courseFile= courseFile.replaceAll("\\\\", "//");//
					outputStream = new FileOutputStream(courseFile+nameDateFormat+"网络设备.xls");
					workbook.write(outputStream);
					outputStream.flush();
					outputStream.close();


				}
				//定义新对象-巡检文件表
				PatrolDocument patrolDocument = new PatrolDocument();
				patrolDocument.setName(nameDateFormat+"网络设备");//文件名称
				patrolDocument.setType("xls");//文件类型
				patrolDocument.setPatrolId(patrolId);//巡检关联id
				patrolDocument.setDocumentUrls("/web/userfiles/"+nameDateFormat+"网络设备.xls");
				patrolDocumentService.save(patrolDocument);//保存
			} catch (Exception e) {
				e.printStackTrace();
			}
			//安全设备
		}else if(i.equals("2")){
			try {
				Long start =System.currentTimeMillis();
				String title = "安全设备";
				Resource resource=new Resource();
				resource.setSelectResourceTypeCodeIds("6");
				List<Resource> list = resourceService.findList(resource);

				String[] head = {};

				if(j.equals("0,")){
					head = new String[]{"安全设备名称", "管理IP", "平均健康度", "可用率", "非健康时长", "非健康起始时间", "异常等级",
							"异常项", "ICMP响应时间"};
				}else if(j.equals("1,")){
					head = new String[]{"安全设备名称", "管理IP", "平均健康度", "可用率", "非健康时长", "非健康起始时间", "异常等级",
							"异常项", "CPU利用率"};
				}else if (j.equals("2,")){
					head = new String[]{"安全设备名称", "管理IP", "平均健康度", "可用率", "非健康时长", "非健康起始时间", "异常等级",
							"异常项", "MEN利用率"};
				}else if(j.equals("0,1,")) {
					head = new String[]{"安全设备名称", "管理IP", "平均健康度", "可用率", "非健康时长", "非健康起始时间", "异常等级",
							"异常项", "ICMP响应时间","CPU利用率"};
				}else if(j.equals("0,2,")) {
					head = new String[]{"安全设备名称", "管理IP", "平均健康度", "可用率", "非健康时长", "非健康起始时间", "异常等级",
							"异常项", "ICMP响应时间","MEN利用率"};
				} else if(j.equals("1,2,")) {
					head = new String[]{"安全设备名称", "管理IP", "平均健康度", "可用率", "非健康时长", "非健康起始时间", "异常等级",
							"异常项", "CPU利用率","MEN利用率"};
				}else if(j.equals("0,1,2,")) {
					head = new String[]{"安全设备名称", "管理IP", "平均健康度", "可用率", "非健康时长", "非健康起始时间", "异常等级",
							"异常项", "ICMP响应时间","CPU利用率","MEN利用率"};
				}
				HSSFWorkbook workbook = new HSSFWorkbook(); //产生工作簿对象
				HSSFSheet sheet = workbook.createSheet("安全设备名称"); //产生工作表对象
				setSheetStyle(sheet);
				// 设置单元格类型
				HSSFCellStyle cellStyle = workbook.createCellStyle();
				setHeadStyle(cellStyle, workbook);

				HSSFCellStyle cellStyleRed = workbook.createCellStyle();
				setHeadStyleRed(cellStyleRed, workbook);

				HSSFCellStyle cellStyleYellow = workbook.createCellStyle();
				setHeadStyleYellow(cellStyleYellow, workbook);

				//产生一行
				HSSFRow row1 = sheet.createRow((short) 0);
				//添加表头
				//添加表头
				for (int k = 0; k < head.length; k++) {
					Cell cell1 = row1.createCell(k);
					cell1.setCellValue(head[k]);
					cell1.setCellStyle(cellStyle);
				}

				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				DecimalFormat df = new DecimalFormat("######0.00");

				for (int k = 0; k < list.size(); k++) {
					row1 = sheet.createRow(k + 1);
					Resource r = list.get(k);
					Cell cellName =row1.createCell(0);
					cellName.setCellValue(r.getName());

					row1.createCell(1).setCellValue(r.getIp());
					List<HealthDegree> healthDegreeList=resourceService.findHealthDegreeList(r.getId());
					Double healthDegreeTotal=0.0;
					for(HealthDegree h:healthDegreeList){
						healthDegreeTotal+=Transformation.null2Double(h.getHealthDegree());
					}

					List<AvailabilityRate> availabilityRateList=resourceService.findAvailabilityRateList(r.getId());

					Double availabilityRateTotal=0.0;
					for(AvailabilityRate h:availabilityRateList){
						availabilityRateTotal+=Transformation.null2Double(h.getAvailabilityRate());
					}

					if(healthDegreeList.size()>0){
						row1.createCell(2).setCellValue(df.format(Transformation.null2Double(healthDegreeTotal)/healthDegreeList.size()));
					}else{
						row1.createCell(2).setCellValue("-");
					}


					if(healthDegreeList.size()>0){
						row1.createCell(3).setCellValue(df.format(Transformation.null2Double(availabilityRateTotal)/availabilityRateList.size()));
					}else{
						row1.createCell(3).setCellValue("-");
					}
					String unHealthStartTime=null;
					Long unHealthTotalTime=0L;
					Long time=0L;
					for(HealthDegree h:healthDegreeList){
						if(Transformation.null2Double(h.getHealthDegree())<100.00){
							if(unHealthStartTime==null){
								unHealthStartTime=sdf.format(h.getCreateDate());
							}
							//unHealth=true;
							if(time>0L){
								unHealthTotalTime+=h.getCreateDate().getTime()-time;
								time=0l;
							}
							time=h.getCreateDate().getTime();
						}else{
							if(time>0L){
								unHealthTotalTime+=h.getCreateDate().getTime()-time;
								time=0l;
							}
						}
					}

					if(time>0L){
						unHealthTotalTime+=System.currentTimeMillis()-time;
						time=0l;
					}
					row1.createCell(4).setCellValue(formatTime(unHealthTotalTime));
					row1.createCell(5).setCellValue(unHealthStartTime);

					List<ResourceException> exceptionList=resourceExceptionService.findEListByResource(r.getId(),null);
					if(exceptionList == null || exceptionList.size()==0){
						row1.createCell(6).setCellValue("正常");
						row1.createCell(7).setCellValue("-");

					}else {
						row1.createCell(6).setCellValue(exceptionList.get(0).getExceptionClass());
						row1.createCell(7).setCellValue(exceptionList.get(0).getIndicatorName());
					}
					//通断-ICMP响应时间
					if(j.equals("0,")){
						Cell responseTime  = row1.createCell(8);
						ResponseTime icmpTime = resourceService.getTopResponseTime(r.getId());
						responseTime.setCellValue(icmpTime.getTime()+"ms");
						//CPU利用率
					}else if(j.equals("1,")){
						Cell cpu = row1.createCell(8);
						String usedRate=cpuService.findCpuUsedRateNew(r.getId());
						if(usedRate==null || usedRate ==""){
							usedRate="0.00";
						}
						cpu.setCellValue(usedRate+"%");

						//MEM利用率
					}else if (j.equals("2,")){
						Cell memory = row1.createCell(8);
						MemoryUsedRate memoryUsedRate= memoryService.getTopMemoryUsedRate(r.getId());
						memory.setCellValue(memoryUsedRate.getUsedRate()+"%");
						//通断-ICMP响应时间 CPU利用率
					}else if(j.equals("0,1,")) {
						Cell responseTime  = row1.createCell(8);
						ResponseTime icmpTime = resourceService.getTopResponseTime(r.getId());
						responseTime.setCellValue(icmpTime.getTime()+"ms");
						Cell cpu = row1.createCell(9);
						String usedRate=cpuService.findCpuUsedRateNew(r.getId());
						if(usedRate==null || usedRate ==""){
							usedRate="0.00";
						}
						cpu.setCellValue(usedRate+"%");
						//通断-ICMP响应时间 	MEM利用率
					}else if(j.equals("0,2,")) {
						Cell responseTime  = row1.createCell(8);
						ResponseTime icmpTime = resourceService.getTopResponseTime(r.getId());
						responseTime.setCellValue(icmpTime.getTime()+"ms");
						Cell memory = row1.createCell(9);
						MemoryUsedRate memoryUsedRate= memoryService.getTopMemoryUsedRate(r.getId());
						memory.setCellValue(memoryUsedRate.getUsedRate()+"%");
						//CPU利用率 	MEM利用率
					} else if(j.equals("1,2,")) {
						Cell cpu = row1.createCell(8);
						String usedRate=cpuService.findCpuUsedRateNew(r.getId());
						if(usedRate==null || usedRate ==""){
							usedRate="0.00";
						}
						cpu.setCellValue(usedRate+"%");
						Cell memory = row1.createCell(9);
						MemoryUsedRate memoryUsedRate= memoryService.getTopMemoryUsedRate(r.getId());
						memory.setCellValue(memoryUsedRate.getUsedRate()+"%");
						//通断-ICMP响应时间   CPU利用率 	MEM利用率
					}else if(j.equals("0,1,2,")) {
						Cell responseTime  = row1.createCell(8);
						ResponseTime icmpTime = resourceService.getTopResponseTime(r.getId());
						responseTime.setCellValue(icmpTime.getTime()+"ms");
						Cell cpu = row1.createCell(9);
						String usedRate=cpuService.findCpuUsedRateNew(r.getId());
						if(usedRate==null || usedRate ==""){
							usedRate="0.00";
						}
						cpu.setCellValue(usedRate+"%");
						Cell memory = row1.createCell(10);
						MemoryUsedRate memoryUsedRate= memoryService.getTopMemoryUsedRate(r.getId());
						memory.setCellValue(memoryUsedRate.getUsedRate()+"%");

					}
					FileOutputStream outputStream;
					File directory = new File("");//参数为空
					String courseFile = directory.getCanonicalPath()+"//src//main//webapp//userfiles//";
					courseFile= courseFile.replaceAll("\\\\", "//");//
					outputStream = new FileOutputStream(courseFile+nameDateFormat+"安全设备.xls");
					workbook.write(outputStream);
					outputStream.flush();
					outputStream.close();
				}
				//定义新对象-巡检文件表
				PatrolDocument patrolDocument = new PatrolDocument();
				patrolDocument.setName(nameDateFormat+"安全设备");//文件名称
				patrolDocument.setType("xls");//文件类型
				patrolDocument.setPatrolId(patrolId);//巡检关联id
				patrolDocument.setDocumentUrls("/web/userfiles/"+nameDateFormat+"安全设备.xls");
				patrolDocumentService.save(patrolDocument);//保存
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


	}


	private void setSheetStyle(HSSFSheet sheet) {
		sheet.setColumnWidth(0,100 * 50);
		sheet.setColumnWidth(1,100 * 50);
		sheet.setColumnWidth(2,100 * 50);
		sheet.setColumnWidth(3,100 * 50);
		sheet.setColumnWidth(4,100 * 50);
		sheet.setColumnWidth(5,100 * 50);
		sheet.setColumnWidth(6,100 * 50);
		sheet.setColumnWidth(7,100 * 50);
		sheet.setColumnWidth(8,100 * 50);
		sheet.setColumnWidth(9,100 * 50);
		sheet.setColumnWidth(10,100 * 50);
	}

	private void setHeadStyle(HSSFCellStyle cellStyle,HSSFWorkbook workbook ) {
		cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFFont font = workbook.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 10);// 设置字体大小
		cellStyle.setFont(font);
	}

	private void setHeadStyleYellow(HSSFCellStyle cellStyle,HSSFWorkbook workbook ) {
		cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		HSSFFont font = workbook.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 10);// 设置字体大小
		cellStyle.setFont(font);
	}


	private void setHeadStyleRed(HSSFCellStyle cellStyle,HSSFWorkbook workbook ) {
		cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		HSSFFont font = workbook.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 10);// 设置字体大小
		cellStyle.setFont(font);
	}




}
