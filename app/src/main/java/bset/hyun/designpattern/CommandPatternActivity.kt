package bset.hyun.designpattern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/*
    커맨드 패턴 (Command Pattern)

    실행될 기능을 캡슐화함으로써 주어진 여러 기능을 수행할 수 있는 재사용성이 높은 클래스를 설계하는 패턴
    즉, 이벤트가 발생했을 때 실행될 기능이 다양하면서도 변경이 필요한 경우에 이벤트를 발생시키는 클래스를 변경하지 않고 재사용하고자 할 때 유용하다.

    실행될 기능을 캡슐화함으로써 기능의 실행을 요구하는 호출자(Invoker)클래스와 실제기능을 수행하는 (Receiver) 클래스 사이의 의존성을 제거한다.
    따라서 실행될 기능의 변경에도 호출자 클래스를 수정없이 그대로 사용할 수 있도록 해준다.
 */

class CommandPatternActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_command_pattern)
    }
}
