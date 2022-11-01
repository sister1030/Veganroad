import controller.MemberController;

public class Runner {

    private static MemberController memberController = MemberController.getInstance();

    public static void main(String[] args) {
        memberController.loginView();
    }

}
