package controller;

import lombok.RequiredArgsConstructor;
import model.MemberDTO;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import service.MemberService;
import view.member.Join;
import view.member.Login;
import view.Main;
import view.member.MyPage;
import view.member.VeganTest;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    public void mainView() {
        new Main();
    }

    public void loginView() {
        new Login();
    }

    public int login(MemberDTO input) {
        return memberService.login(input);
    }

    public void joinView() {
        new Join();
    }

    public void veganTest(MemberDTO input) {
        new VeganTest(input);
    }

    public int join(MemberDTO input) {
        return memberService.join(input);
    }

    public int idCheck(String id) {
        return memberService.idCheck(id);
    }

    public void updateView() {
        new MyPage();
    }

    public MemberDTO getMember(String id) {
        return memberService.getMember(id);
    }

    public int update(MemberDTO input) {
        return memberService.update(input);
    }

    public void quit(String id) {
        memberService.quit(id);
    }

    private static MemberController instance;

    public static MemberController getInstance() {
        if (instance == null)
            instance = new ClassPathXmlApplicationContext("bean-config.xml").getBean("memberController", MemberController.class);

        return instance;
    }

}
