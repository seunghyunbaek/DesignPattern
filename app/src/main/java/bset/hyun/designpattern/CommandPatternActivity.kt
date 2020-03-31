package bset.hyun.designpattern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_command_pattern.*

/*
    커맨드 패턴 (Command Pattern)

    실행될 기능을 캡슐화함으로써 주어진 여러 기능을 수행할 수 있는 재사용성이 높은 클래스를 설계하는 패턴
    즉, 이벤트가 발생했을 때 실행될 기능이 다양하면서도 변경이 필요한 경우에 이벤트를 발생시키는 클래스를 변경하지 않고 재사용하고자 할 때 유용하다.

    실행될 기능을 캡슐화함으로써 기능의 실행을 요구하는 호출자(Invoker)클래스와 실제기능을 수행하는 (Receiver) 클래스 사이의 의존성을 제거한다.
    따라서 실행될 기능의 변경에도 호출자 클래스를 수정없이 그대로 사용할 수 있도록 해준다.
 */


/*
    버튼이 눌리면 램프의 불이 켜지는 프로그램
        문제점
            1. 버튼을 눌렀을 때 다른 기능을 실행하는 경우
                - 새로운 기능으로 변경하려면 기존 코드의 내용을 수정해야 하므로 OCP에 위배된다.
                - 버튼 클래스의 pressed() 전체를 변경해야 한다.
            2. 버튼을 누르는 동작에 따라 다른 기능을 실행하는 경우
                - 버튼을 처음 눌렀을 때는 램프를 켜고, 두 번째 눌렀을 때는 알람을 동작하게 하려면
                  필요한 기능을 새로 추가할 때마다 Button 클래스의 코드를 수정해야 하므로 재사용하기 어렵다.

        해결책
            문제를 해결하기 위해서는 구체적인 기능을 직접 구현하는 대신 실행될 기능을 캡슐화해야 한다.
            즉, Button 클래스의 pressed 메서드에서 구체적인 기능(램프 켜기, 알람 동작 등)을 직접 구현하는 대신 버튼을 눌렀을 때 실행될 기능을 Button 클래스 외부에서 제공받아 캡슐화해 pressed 메서드에서 호출한다.
            이를 통해 Button 클래스 코드를 수정하지 않고 그대로 사용할 수 있다.

        Button 클래스는 미리 약속된 Command 인터페이스의 execute 메서드를 호출한다.
            - 램프를 켜는 경우에는 theLamp.turnOn()을 호출하고
            - 알람이 동작하는 경우에는 theAlarm.start()를 호출하도록 pressed 메서드를 수정한다.
        LampOnCommand 클래스에서는 Command 인터페이스의 execute 메서드를 구현해 Lamp 클래스의 turnOn()을 호출한다.
        AlarmStartCommand 클래스는 Command 인터페이스의 execute 메서드를 구현해 Alarm 클래스의 start()를 호출한다.

        Command 인터페이스를 구현하는 LampOnCommand, AlarmStartCommand 객체를 CommandButton객체에 설정한다.
        Button 클래스의 pressed()에서 Command 인터페이스의 execute()를 호출한다.
        즉, 버튼을 눌렀을 때 필요한 임의의 기능은 Command 인터페이스를 구현한 클래스의 객체를 Button 객체에 설정해서 실행할 수 있다.

        이렇게 Command패턴을 이용하면 Button 클래스의 코드를 변경하지 않으면서 다양한 동작을 구현할 수 있게 된다.
*/

interface Command { abstract fun execute() }

enum class Mode { LAMP, ALARM }
class Lamp { fun turnOn() {System.out.println("Lamp On")} }
class Alarm { fun start() { System.out.println("Alarming") } }
class Button {
    private lateinit var theLamp: Lamp
    private lateinit var theAlarm: Alarm
    private lateinit var theMode: Mode
    constructor(theLamp:Lamp) { this.theLamp = theLamp }
    constructor(theAlarm:Alarm) { this.theAlarm = theAlarm }
    constructor(theLamp: Lamp, theAlarm: Alarm) {
        this.theLamp = theLamp
        this.theAlarm = theAlarm
    }
    fun setMode(mode:Mode) { this.theMode = mode }
    fun pressed() {
        when(theMode) {
            Mode.LAMP -> theLamp.turnOn()
            Mode.ALARM -> theAlarm.start()
        }
    }
}

class CommandButton {
    private lateinit var theCommand:Command
    // 버튼을 눌렀을 때 필요한 기능을 인자로 받는다.
    constructor(theCommand:Command) { setCommand(theCommand) }
    fun setCommand(newCommand:Command) { this.theCommand = newCommand }
    // 버튼이 눌리면 주어진 Command의 execute()를 호출한다.
    fun pressed() { theCommand.execute() }
}

class LampOnCommand: Command {
    private lateinit var theLamp: Lamp
    constructor(theLamp:Lamp) { this.theLamp = theLamp }
    override fun execute() { theLamp.turnOn() }
}

class AlarmStartCommand: Command {
    private lateinit var theAlarm: Alarm
    constructor(theAlarm: Alarm) { this.theAlarm = theAlarm }
    override fun execute() { theAlarm.start() }

}

class CommandPatternActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_command_pattern)

        btn_lamp.setOnClickListener{
            val lamp = Lamp()
            val lampButton = Button(lamp)
            lampButton.pressed()
        }

        btn_command.setOnClickListener{
            val lamp = Lamp()
            val lampOnCommand = LampOnCommand(lamp)
            val alarm = Alarm()
            val alarmStartCommand = AlarmStartCommand(alarm)

            val button1 = CommandButton(lampOnCommand) // 램프 켜는 커맨드 설정
            button1.pressed() // 램프 켜기

            val button2 = CommandButton(alarmStartCommand) // 알람 울리는 Command 설정
            button2.pressed() // 알람 시작
            button2.setCommand(lampOnCommand) // 램프 켜는 커맨드로 설정
            button2.pressed() // 램프 켜기
        }

    }
}
