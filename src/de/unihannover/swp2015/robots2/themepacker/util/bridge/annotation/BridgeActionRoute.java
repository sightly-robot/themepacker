package de.unihannover.swp2015.robots2.themepacker.util.bridge.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.unihannover.swp2015.robots2.themepacker.util.bridge.ActionEvent;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BridgeActionRoute {
	
	ActionEvent value();

}
