package org.landvibe.ass1.controller;

import org.landvibe.ass1.dummy.Dummy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class controller {
    List<Dummy> data = new ArrayList<>();
    int usage = 0;

    /**
     * 자바프로그램 메모리 사용량을 대략적으로 출력합니다.
     */
    @GetMapping()
    public String usage(){
        return Integer.toString(usage)+"MB만큼 사용중입니다.";
    }

    /**
     * count만큼 자바프로그램의 메모리 사용량을 증가시킵니다.
     * @param count 증가시킬 메모리 사용량
     */
    @GetMapping("/{count}")
    public String increase(@PathVariable("count") int count){
        if(count >500){
            return "500 미만의 값만 입력해주세요. 당신의 컴퓨터는 슈퍼컴퓨터입니까?";
        }
        Dummy dummy = new Dummy(count);
        data.add(dummy);
        usage += count;
        return Integer.toString(count)+"MB만큼 메모리 사용량이 증가했습니다.";
    }

    /**
     * 점유중인 메모리들의 참조를 해제합니다.
     */
    @GetMapping("/clear")
    public String clear(){
        data.clear();
        usage = 0;
        return "메모리들의 참조를 모두 해제했습니다.";
    }

    /**
     * gc를 수행합니다.
     */
    @GetMapping("/gc")
    public String gc(){
        System.gc();
        return "gc를 수행했습니다.";
    }
}