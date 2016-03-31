package com.zjjf.analysis.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.Toolbox;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.feature.Restore;
import com.github.abel533.echarts.series.Pie;
import com.zjjf.analysis.services.echarts.pie.PieEcharts;
import com.zjjf.analysis.services.impl.datamodle.PieDataService;
import com.zjjf.analysis.utils.ZfEcharts;

@Service
public class PieEchartsOptionServiceImpl extends PieEcharts {
	
	@Autowired
	private PieDataService pieDataService;
	
	public Option getOption(ZfEcharts type) {
		
		Option option = new Option();
		this.setOption(option);
		this.setTitle2Option();
		this.setTooltip2Option();
		this.setLegend2Option();
		this.setToolbox2Option();
		this.setSeries2Option();
		return option;
	}
	
	public void setTitle2Option() {
		
		Title title = new Title();
		title.setText("EChartsʵ��");
		title.setSubtext("��ͼ��Pie Chart��");
		title.setX("center");
		super.setTitle2Option(title);
	}

	public void setTooltip2Option() {
		Tooltip tooltip = new Tooltip();
		tooltip.setTrigger(Trigger.item);
		tooltip.setFormatter("{a} <br/>{b} : {c} ({d}%)");
		super.setTooltip2Option(tooltip);
	}

	public void setLegend2Option() {
		
		Legend legend = new Legend();
		legend.setOrient(Orient.vertical);
		legend.setX("left");
		legend.data(Arrays.asList("part1", "part2", "part3", "part4"));
		super.setLegend2Option(legend);
	}

	public void setToolbox2Option() {
		
		Toolbox toolbox = new Toolbox();
		toolbox.setShow(true);
		toolbox.feature(getRestore());
		super.setToolbox2Option(toolbox);
	}
	
	private Restore getRestore() {

		Restore restore = new Restore();
		restore.setShow(true);
		return restore;
	}

	
	public void setSeries2Option() {
		
		List<Pie> pieList = new ArrayList<Pie>();
		Object center[] = { "50%", "60%" };
		Pie pie = new Pie();
		pie.setName("��ͼʵ��");
		pie.setType(SeriesType.pie);
		pie.setRadius("55%");
		pie.setCenter(center);
		pie.setData(pieDataService.simulationData());
		pieList.add(pie);
		super.setSeries2Option(pieList);
	}

}
