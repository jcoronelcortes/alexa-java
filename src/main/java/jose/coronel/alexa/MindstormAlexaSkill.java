package jose.coronel.alexa;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import jose.coronel.alexa.handlers.common.*;
import jose.coronel.alexa.handlers.mindstom.MoveIntentHandler;
import jose.coronel.alexa.handlers.mindstom.SetCommandIntentHandler;
import jose.coronel.alexa.handlers.mindstom.SetSpeedIntentHandler;

public class MindstormAlexaSkill extends SkillStreamHandler {

    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                        new LaunchRequestHandler(),
                        new SetSpeedIntentHandler(),
                        new SetCommandIntentHandler(),
                        new MoveIntentHandler(),
                        new HelpIntentHandler(),
                        new CancelandStopIntentHandler(),
                        new SessionEndedRequestHandler(),
                        new FallbackIntentHandler(),
                        new IntentReflectorHandler())
                .build();
    }

    public MindstormAlexaSkill() {
        super(getSkill());
    }

}
