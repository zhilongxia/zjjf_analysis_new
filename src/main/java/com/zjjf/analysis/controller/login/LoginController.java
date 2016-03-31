package com.zjjf.analysis.controller.login;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjjf.analysis.beans.analysis.users.UserInfos;
import com.zjjf.analysis.beans.login.ScManager;
import com.zjjf.analysis.beans.vo.LoginVo;
import com.zjjf.analysis.beans.vo.ModelMsg;
import com.zjjf.analysis.constants.LoginConstant;
import com.zjjf.analysis.constants.SessionConfig;
import com.zjjf.analysis.controller.BaseController;
import com.zjjf.analysis.services.security.AuthorityServiceImpl;
import com.zjjf.analysis.utils.ResponseUtils;

@Controller
@RequestMapping(value = "/analysis/authority")
public class LoginController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	AuthorityServiceImpl authorityService;
    
	@RequestMapping(value = "/scmsLoginPage.do")
	@ResponseBody
	public Object scMgLoginIn(LoginVo loginRo,  HttpSession session,HttpServletRequest request,Model model) {
		//����У��
		if (loginRo == null || StringUtils.isEmpty(loginRo.getUserName()) || StringUtils.isEmpty(loginRo.getPassword()) ) {
			return ResponseUtils.sendMsg(false, "�������û���������½��");
		}
		//��ֹ�ظ���½
		ScManager scmser = getCurrentUser(ScManager.class,request);
		if (scmser != null) {
			return ResponseUtils.sendMsg(true,"�û��ѵ�½", LoginConstant.already_login_url);
		}
		//У����֤��
		if (StringUtils.isEmpty(loginRo.getCheckCode())) {
			session.removeAttribute(SessionConfig.user_session_code);
			return ResponseUtils.sendMsg(false, "��֤�벻��Ϊ��");
		}
		String serverCode = (String) session.getAttribute(SessionConfig.user_session_code);
		if (serverCode == null || !serverCode.toLowerCase().equals(loginRo.getCheckCode().trim().toLowerCase())) {
			session.removeAttribute(SessionConfig.user_session_code);
			return ResponseUtils.sendMsg(false, "��֤�����");
		}
		//�����½
		try {
			UsernamePasswordToken logintoken = new UsernamePasswordToken(loginRo.getUserName(), loginRo.getPassword(), true);
			try {
				SecurityUtils.getSubject().login(logintoken);
			}catch(UnknownAccountException une){
				return ResponseUtils.sendMsg(false, "�Բ�������û��ע��");
			}catch (Exception e) {
				logger.error("�û���½�쳣��{}",loginRo.getUserName(),e);
				return ResponseUtils.sendMsg(false, "�û��������벻��ȷ");
			}
			ModelMsg msg = authorityService.dealSuccessLogin(loginRo,request,model);
			if(msg != null && msg.isSuccess()){
				return ResponseUtils.sendMsg(true, "��½�ɹ�", LoginConstant.already_login_url);				
			}else{				
				return ResponseUtils.sendMsg(false, msg.getMessage());				
			}
		} catch (Exception e) {
			logger.error("�û���½�����쳣��",e);
			return ResponseUtils.sendMsg(false, "��½�쳣");
		}
	}
	
	@RequestMapping(value = "/scMgLoginIn.do")
	public String gotoLoginPage(HttpServletRequest request) throws IOException {

		return LoginConstant.main_url;
	}

	@RequestMapping(value = "/scmsMainPage.do")
	public String index(HttpServletRequest request, Model model) {

//		Subject subject = SecurityUtils.getSubject();
//		if(subject.isAuthenticated()){
//			LoginVo logInVo = new LoginVo();
//			UserInfos user =(UserInfos)subject.getSession().getAttribute(SessionConfig.user_session_userInfo);
//			if(user == null){
//				return LoginConstant.login_url;
//			}else{
//				logInVo.setUserId(user.getId());
//				logInVo.setUserName(user.getUserName());
//			}
//			model.addAttribute("logInVo", logInVo);
//			return LoginConstant.main_url;
//		}else{
			return LoginConstant.login_url;
//		}
	}

	@RequestMapping(value = "/toMainPage.do", method = RequestMethod.GET)
	public String toMain() {
		try {
			return "index/index";
		} catch (Exception e) {
			// TODO ����ҳ
			return "";
		}
	}

	/**
	 * ��ȡ��ά��
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = "/checkcode.do", method = RequestMethod.GET)
	public void checkcode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// ��ֹ����
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		// ָ�����ɵ���Ӧ��ͼƬ
		response.setContentType("image/jpeg");
		int width = 120;
		int height = 35;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // ����BufferedImage��Ķ���
		Graphics g = image.getGraphics(); // ����Graphics��Ķ���
		Graphics2D g2d = (Graphics2D) g; // ͨ��Graphics��Ķ��󴴽�һ��Graphics2D��Ķ���
		Random random = new Random(); // ʵ����һ��Random����
		Font mFont = new Font("��������", Font.BOLD, 30); // ͨ��Font��������
		g.setColor(getRandColor(200, 250)); // �ı�ͼ�εĵ�ǰ��ɫΪ������ɵ���ɫ
		g.fillRect(0, 0, width, height); // ����һ����ɫ����

		// ��һ������
		BasicStroke bs = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL); // ����һ��������ѡ��������ϸ�Ķ���
		g2d.setStroke(bs); // �ı������Ĵ�ϸ
		g.setColor(Color.DARK_GRAY); // ���õ�ǰ��ɫΪԤ������ɫ�е����ɫ
		int[] xPoints = new int[3];
		int[] yPoints = new int[3];
		for (int j = 0; j < 3; j++) {
			xPoints[j] = random.nextInt(width - 1);
			yPoints[j] = random.nextInt(height - 1);
		}
		g.drawPolyline(xPoints, yPoints, 3);
		// ���ɲ�����������֤����
		g.setFont(mFont);
		String sRand = "";
		int itmp = 0;
		for (int i = 0; i < 4; i++) {
			if (random.nextInt(2) == 1) {
				itmp = random.nextInt(26) + 65; // ����A~Z����ĸ
			} else {
				itmp = random.nextInt(10) + 48; // ����0~9������
			}
			char ctmp = (char) itmp;
			sRand += String.valueOf(ctmp);
			Color color = new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110));
			g.setColor(color);
			g.drawString(String.valueOf(ctmp), 30 * i + 10, 25);

		}
		g.dispose();
		// �����ɵ���֤�뱣�浽Session��
		HttpSession session = request.getSession(true);
		session.setAttribute(SessionConfig.user_session_code, sRand);
		System.out.println("���ɵ�½��֤��{}" + sRand);
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}

	private Color getRandColor(int a, int b) {
		Random random = new Random();
		Color color = new Color(50 + random.nextInt(110), a, b);
		return color;
	}

	@RequestMapping(value = "/supplierLoginIn.do")
	@ResponseBody
	public Object supplierLoginIn(LoginVo loginRo, HttpSession session, HttpServletRequest request, Model model) {
		// ����У��
		if (loginRo == null || StringUtils.isEmpty(loginRo.getUserName()) || StringUtils.isEmpty(loginRo.getPassword())) {
			return ResponseUtils.sendMsg(false, "�������û���������½��");
		}
		// ��ֹ�ظ���½
		UserInfos sp = getCurrentUser(UserInfos.class, request);
		if (sp != null) {
			return ResponseUtils.sendMsg(true, "�û��ѵ�½", LoginConstant.already_login_url);
		}
		// У����֤��
		if (StringUtils.isEmpty(loginRo.getCheckCode())) {
			session.removeAttribute(SessionConfig.user_session_code);
			return ResponseUtils.sendMsg(false, "��֤�벻��Ϊ��");
		}
		String serverCode = (String) session.getAttribute(SessionConfig.user_session_code);
		if (serverCode == null || !serverCode.toLowerCase().equals(loginRo.getCheckCode().trim().toLowerCase())) {
			session.removeAttribute(SessionConfig.user_session_code);
			return ResponseUtils.sendMsg(false, "��֤�����");
		}
		// �����½
		try {
			UsernamePasswordToken logintoken = new UsernamePasswordToken(loginRo.getUserName(), loginRo.getPassword(), true);
			try {
				SecurityUtils.getSubject().login(logintoken);
			} catch (UnknownAccountException une) {
				return ResponseUtils.sendMsg(false, "�Բ�������û��ע��");
			} catch (Exception e) {
				logger.error("�û���½�쳣��{}", loginRo.getUserName(), e);
				return ResponseUtils.sendMsg(false, "�û��������벻��ȷ");
			}
			// ModelMsg msg =
			// authorityService.dealSupplierSuccessLogin(loginRo,request,model);
			ModelMsg msg = authorityService.dealSuccessLogin(loginRo, request, model);
			if (msg != null && msg.isSuccess()) {
				return ResponseUtils.sendMsg(true, "��½�ɹ�", LoginConstant.already_login_url);
			} else {
				return ResponseUtils.sendMsg(false, msg.getMessage());
			}
		} catch (Exception e) {
			logger.error("�û���½�����쳣��", e);
			return ResponseUtils.sendMsg(false, "��½�쳣");
		}
	}

}
