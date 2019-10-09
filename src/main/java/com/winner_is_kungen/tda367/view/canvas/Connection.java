package com.winner_is_kungen.tda367.view.canvas;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

public class Connection extends Line {
	double originX;
	double originY;
	double destX;
	double destY;

	public Connection(){
		super();
	}

	public void setOrigin(Point2D origin){
		this.originX = origin.getX();
		this.originY = origin.getY();
	}

	public void setDest(Point2D dest){
		this.destX = dest.getX();
		this.destY = dest.getY();
	}

	public void applyShape(){
		setStartX(this.originX);
		setStartY(this.originY);
		setEndX(this.destX);
		setEndY(this.destY);
	}

	public void applyOffset(){
		double lineOffsetX;
		double lineOffsetY;

		if(originX < destX){
			lineOffsetX = originX;
		}else{
			lineOffsetX = destX;
		}
		if(originY < destY){
			lineOffsetY = originY;
		}else{
			lineOffsetY = destY;
		}

		setTranslateX(lineOffsetX);
		setTranslateY(lineOffsetY);
	}
}
