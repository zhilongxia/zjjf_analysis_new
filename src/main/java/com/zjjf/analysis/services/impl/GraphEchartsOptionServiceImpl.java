package com.zjjf.analysis.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.Toolbox;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.axis.SplitArea;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.feature.DataView;
import com.github.abel533.echarts.feature.DataZoom;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.feature.Mark;
import com.github.abel533.echarts.feature.Restore;
import com.github.abel533.echarts.feature.SaveAsImage;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.MarkLine;
import com.github.abel533.echarts.series.MarkPoint;
import com.zjjf.analysis.services.echarts.Echarts;
import com.zjjf.analysis.services.impl.datamodle.GraphDataService;
import com.zjjf.analysis.utils.ZfEcharts;

@Service
public class GraphEchartsOptionServiceImpl extends Echarts<Line> {
	
	@Autowired
	private GraphDataService graphDataService;
	
	public Option getOption(ZfEcharts type) {
		
		Option option = new Option();
		this.setOption(option);
		this.setTitle2Option();
		this.setTooltip2Option();
		this.setLegend2Option();
		this.setToolbox2Option();
		this.setXAxis2Option();
		this.setYAxis2Option();
		this.setSeries2Option();
		return option;
	}

	public void setTitle2Option() {
		
		Title title = new Title();
		title.setText("�������ݻ���");
		title.setLink("http://www.tqyb.com.cn/weatherLive/climateForecast/2014-01-26/157.html");
		title.setSubtext("www.stepday.com");
		title.setSublink("http://www.stepday.com/myblog/?Echarts");
		title.setX("left");
		title.setY("top");
		super.setTitle2Option(title);
	}

	public void setTooltip2Option() {

		Tooltip tooltip = new Tooltip();
		tooltip.setTrigger(Trigger.axis);
		super.setTooltip2Option(tooltip);
	}

	public void setLegend2Option() {
		
		Legend legend = new Legend();
		legend.setShow(true);
		legend.setX("center");
		legend.setY("top");
		legend.data(Arrays.asList("������", "��ˮ��"));
		super.setLegend2Option(legend);
	}
	
	public void setToolbox2Option() {
		
		Toolbox toolbox = new Toolbox();
		toolbox.setShow(true);
		toolbox.feature(getMark(), getDataZoom(), getDataView(), getMagicType(), getRestore(), getSaveAsImage());
		super.setToolbox2Option(toolbox);
	}

	private Mark getMark() {
		Mark mark = new Mark();
		mark.setShow(true);
		return mark;
	}

	private DataZoom getDataZoom() {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dataZoom", "��������");
		map.put("dataZoomReset", "�������ź���");
		DataZoom dataZoom = new DataZoom();
		dataZoom.setShow(true);
		dataZoom.setTitle(map);
		return dataZoom;
	}

	private DataView getDataView() {

		DataView dataView = new DataView();
		dataView.setShow(true);
		dataView.setReadOnly(true);
		return dataView;
	}

	private MagicType getMagicType() {

		List<String> _list = new ArrayList<String>();
		_list.add("line");
		_list.add("bar");
		MagicType magicType = new MagicType();
		magicType.setShow(true);
		magicType.setType(_list);
		return magicType;
	}

	private Restore getRestore() {

		Restore restore = new Restore();
		restore.setShow(true);
		return restore;
	}

	private SaveAsImage getSaveAsImage() {

		SaveAsImage saveAsImage = new SaveAsImage();
		saveAsImage.setShow(true);
		return saveAsImage;
	}
	
	public void setSeries2Option() {
		
		List<Line> lineList = new ArrayList<Line>();
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("type", "max");
		map.put("name", "���ֵ");
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("type", "min");
		map2.put("name", "��Сֵ");

		HashMap<String, Object> averageMap = new HashMap<String, Object>();
		averageMap.put("type", "average");
		averageMap.put("name", "ƽ��ֵ");
		
		Line line = new Line();
		line.setName("���̣��ң�");
		line.setType(SeriesType.bar);
		line.data(graphDataService.getDataModleOne());
		line.markPoint(new MarkPoint().data(map, map2));
		line.markLine(new MarkLine().data(averageMap));

		Line line2 = new Line();
		line2.setName("�µ�");
		line2.setType(SeriesType.bar);
		line2.data(graphDataService.getDataModleTwo());
		line2.markPoint(new MarkPoint().data(map, map2));
		line2.markLine(new MarkLine().data(averageMap));
		
		Line line3 = new Line();
		line3.setName("���۽�Ԫ��");
		line3.setType(SeriesType.bar);
		line3.data(graphDataService.getDataModleOne());
		line3.markPoint(new MarkPoint().data(map, map2));
		line3.markLine(new MarkLine().data(averageMap));
		
		Line line4 = new Line();
		line4.setName("����");
		line4.setType(SeriesType.bar);
		line4.data(graphDataService.getDataModleTwo());
		line4.markPoint(new MarkPoint().data(map, map2));
		line4.markLine(new MarkLine().data(averageMap));
		
		lineList.add(line);
		lineList.add(line2);
		lineList.add(line3);
		lineList.add(line4);
		super.setSeries2Option(lineList);
	}
	
	public void setXAxis2Option() {

		ValueAxis xAxis = new ValueAxis();
		xAxis.setShow(true);
		xAxis.setType(AxisType.category);
		xAxis.data("������","��ɽ��", "������", "ƺɽ", "���", "��ɽת��", "����ת��", "ʯ��", "����", "����");
		super.setXAxis2Option(xAxis);
	}

	public void setYAxis2Option() {

		ValueAxis yAxis = new ValueAxis();
		yAxis.setShow(true);
		yAxis.setType(AxisType.value);
		SplitArea splitArea = new SplitArea();
		splitArea.setShow(true);
		yAxis.splitArea(splitArea);
		super.setYAxis2Option(yAxis);
	}
	
	public void setOption(Option option){
		
		super.setOption(option);
	}

}
