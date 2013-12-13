package com.jkl.game.gdx.oxy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Logger;
import com.jkl.game.gdx.oxy.screens.OxyLevel;

public class OxyContactListener implements ContactListener {
	
	private Logger log = new Logger("ContactListener",Logger.DEBUG);
	
	private Sound sound;
	private OxyTarget lastTarget;

	public OxyContactListener() {
		this.sound = Gdx.audio.newSound(Gdx.files.internal("sound/Hit.mp3"));
//		this.objects = objects;
	}
	
  private void doTarget(OxyTarget t) {
//   log.info("lastTarget ??"+lastTarget);
   if (lastTarget == null) {
	   lastTarget = t;
   }
   t.show = true;
   if (OxyLevel.opentargets ==0) OxyLevel.opentargets++;
   if (lastTarget == t) {
	   return;
   }

   if ( t.color == lastTarget.color) {
			log.info("Same  color");
            if (lastTarget != t) OxyLevel.opentargets++;
	} else {
			log.info("diffrent color");
            if (!lastTarget.solved) {
            	lastTarget.show = false;
            }
	}

   lastTarget = t;
   log.debug("lastTarget now"+lastTarget.color.toString());
}   
		

	
	@Override
	public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
//        Fixture fixtureB = contact.getFixtureB();
//        log.debug("beginContact between " + fixtureA.getUserData().toString() + " and " + fixtureB.getUserData().toString());
        if (fixtureA.getUserData().getClass() == OxyTarget.class ) {
        	OxyTarget target = (OxyTarget) fixtureA.getUserData(); 
        	if (! target.solved) {
        		doTarget(target);
        	}
        }
      sound.play(1.0f);
	}

	@Override
	public void endContact(Contact contact) {
//        Fixture fixtureA = contact.getFixtureA();
//        Fixture fixtureB = contact.getFixtureB();
//        Gdx.app.log("endContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
 	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}

