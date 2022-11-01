package service;

import lombok.RequiredArgsConstructor;
import model.MemberDTO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final SessionFactory factory;

    public int login(MemberDTO member) {
        return factory.getInstance().selectOne("MemberMapper.login", member);
    }

    public int idCheck(String id) {
        return factory.getInstance().selectOne("MemberMapper.idCheck", id);
    }

    public int join(MemberDTO member) {
        return factory.getInstance().insert("MemberMapper.join", member);
    }

    public MemberDTO getMember(String id) {
        return factory.getInstance().selectOne("MemberMapper.getMember", id);
    }

    public int update(MemberDTO member) {
        return factory.getInstance().update("MemberMapper.update", member);
    }

    public void quit(String id) {
        factory.getInstance().update("MemberMapper.quit", id);
    }

}
