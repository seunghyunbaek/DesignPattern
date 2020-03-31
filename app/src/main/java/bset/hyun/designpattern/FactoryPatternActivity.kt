package bset.hyun.designpattern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_factory_pattern.*

/*
    팩토리 패턴 (Factory Pattern)

    모든 팩토리 패턴은 '객체 생성을 캡슐화' 한다.
    팩토리 메소드 패턴
        객체를 생성하기 위한 인터페이스를 정의하는데,
        어떤 클래스의 인스턴스를 만들지는 서브 클래스에서 결정한다.
    추상 팩토리 패턴
        인터페이스를 이용하여 서로 연관된, 또는
        의존하는 객체를 구상 클래스를 지정하지 않고도 생성할 수 있다.

    디자인 원칙 중 '추상화된 것에 의존하도록 만들어라.
    구상 클래스에 의존하지 않도록 만든다' 에 기인한 패턴이다.

    팩토리 패턴의 핵심
    클래스의 인스턴스를 만드는 것을 서브 클래스에서 결정하도록 한다는 것이다.
    객체 생성하는 부분을 서브클래스에 위임함으로서 '객체 생성을 캡슐화' 하고
    구상 클래스에 대한 의존성이 줄어든다는 이점을 얻을 수 있다.

    특히 구상 클래스에 대한 의존성이 줄어드는 것은 의존성 뒤집기 원칙(DI)에 기인하는데,
    DI는 자바 진영에서 널리 쓰이고 있는 Spring 프레임 워크의 핵심 개념 중 하나이다.

    싱글톤 패턴과 더불어 가장 유명하고 널리 쓰이는 디자인 패턴 중 하나라고 할 수 있다.

    이점은 뭘까?
    클래스의 변경사항이 생겼을 때 얼마나 다른 클래스에게도 영향을 줄것인가가 '결합도' 이다.
    팩토리 메소드 패턴은 직접 사용하는 객체를 생성하지 않고 팩토리 메소드 클래스를 통해 객체를 대신 생성하고
    그 객체를 반환 받아 사용하기 때문에 효율적인 코드 제어를 할 수 있을 뿐더러 결합도를 낮춰 유지보수가 용이하다.
 */

class FactoryPatternActivity : AppCompatActivity() {

    /*  팩토리 메소드 패턴 [Start]  */

    abstract class Ramen { abstract fun getName():String }

    class SinRamen : Ramen() { override fun getName(): String = "SinRamen" }
    class NuguriRamen : Ramen() { override fun getName(): String = "NuguriRamen" }
    class SamyangRamen : Ramen() { override fun getName(): String = "SamyangRamen" }
    class JinRamen : Ramen() { override fun getName(): String = "JinRamen" }

    abstract class RamenFactory { abstract fun createRamen(type:String): Ramen? }
    class TypeRamenFactory:RamenFactory() {
        override fun createRamen(type: String): Ramen? {
            var ramen:Ramen? = null

            when(type) {
                "sin" -> { ramen = SinRamen() }
                "nuguri" -> { ramen = NuguriRamen() }
                "samyang" -> { ramen = SamyangRamen() }
                "jin" -> { ramen = JinRamen() }
            }

            return ramen
        }
    }

    /*  팩토리 메소드 패턴 [End]  */
    ///////////////////////////////
    /*  추상 팩토리 패턴 [Start]  */
    interface RamenAbstractFactory { fun createRamen():Ramen }

    class SinRamenFactory:RamenAbstractFactory { override fun createRamen(): Ramen = SinRamen() }
    class NuguriRamenFactory:RamenAbstractFactory { override fun createRamen():Ramen = NuguriRamen() }
    class SamYangRamenFactory:RamenAbstractFactory { override fun createRamen():Ramen = SamyangRamen() }
    class JinRamenFactory:RamenAbstractFactory { override fun createRamen():Ramen = JinRamen() }

    class RamenFactory2 { companion object { fun getRamen(ramenAbstractFactory: RamenAbstractFactory) = ramenAbstractFactory.createRamen()} }
    /*  추상 팩토리 패턴 [End]  */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_factory_pattern)

        /* 팩토리 메소드 패턴 [Start] - Ramen() 생성자를 사용하지 않은 것을 확인할 수 있다 */
        btn_factory_method.setOnClickListener {
            var typeRamenFactory = TypeRamenFactory()
            var inputName = edit_factory_method.text.toString()
            var ramenName = typeRamenFactory.createRamen(inputName)?.getName()
            Toast.makeText(applicationContext, "라면 : ${ramenName}", Toast.LENGTH_SHORT).show()
        }
        /* 팩토리 메소드 패턴 [End] */

        /*  추상 팩토리 패턴 [Start]  */
        RamenFactory2.getRamen(SinRamenFactory())
        RamenFactory2.getRamen(NuguriRamenFactory())
        RamenFactory2.getRamen(SamYangRamenFactory())
        RamenFactory2.getRamen(JinRamenFactory())
        /*  추상 팩토리 패턴 [End]  */

    }
}
