package de.unihannover.swp2015.robots2.themepacker.util.bridge;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method as an event end-point. The event which should lead to an invoke can be set.
 * 
 * @author Rico Schrage
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BridgeUpdateRoute {
	
	/**
	 * Event which leads to an invoke.
	 * 
	 * @return {@link UpdateEvent}
	 */
	UpdateEvent value();

}
