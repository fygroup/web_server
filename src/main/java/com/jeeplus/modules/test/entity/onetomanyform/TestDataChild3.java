
package com.jeeplus.modules.test.entity.onetomanyform;

import com.jeeplus.modules.sys.entity.Area;
import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 汽车票Entity
 * @author liugf
 * @version 2017-06-19
 */
public class TestDataChild3 extends DataEntity<TestDataChild3> {
	
	private static final long serialVersionUID = 1L;
	private Area startArea;		// 出发地
	private Area endArea;		// 目的地
	private Double price;		// 代理价格
	private TestDataMainForm testDataMain;		// 外键 父类
	
	public TestDataChild3() {
		super();
	}

	public TestDataChild3(String id){
		super(id);
	}

	public TestDataChild3(TestDataMainForm testDataMain){
		this.testDataMain = testDataMain;
	}

	@NotNull(message="出发地不能为空")
	@ExcelField(title="出发地", fieldType=Area.class, value="startArea.name", align=2, sort=1)
	public Area getStartArea() {
		return startArea;
	}

	public void setStartArea(Area startArea) {
		this.startArea = startArea;
	}
	
	@NotNull(message="目的地不能为空")
	@ExcelField(title="目的地", fieldType=Area.class, value="endArea.name", align=2, sort=2)
	public Area getEndArea() {
		return endArea;
	}

	public void setEndArea(Area endArea) {
		this.endArea = endArea;
	}
	
	@ExcelField(title="代理价格", align=2, sort=3)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public TestDataMainForm getTestDataMain() {
		return testDataMain;
	}

	public void setTestDataMain(TestDataMainForm testDataMain) {
		this.testDataMain = testDataMain;
	}
	
}