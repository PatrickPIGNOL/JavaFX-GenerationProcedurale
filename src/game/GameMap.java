package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class GameMap 
{
	private List<List<MapCell>> aCells = null;
	private int aLines = 6;
	private int aColumns = 9;
	private double aWidth = 40;
	private double aHeight = 20;
	private double aSpace = 4;
	private int aCellsNumber = 25;
	private MapCell aStartCell;
	private Random aRandom;
	private EShape aShape;
	
	public GameMap()
	{
		this.aRandom = new Random(System.nanoTime());
		this.aShape = EShape.RoundRectangle;
	}
	
	public void mKeyPress(KeyEvent e)
	{
		this.mOnKeyPressed(e);
	}
	
	private void mOnKeyPressed(KeyEvent e)
	{
		if(e.getCode() == KeyCode.SPACE)
		{
			this.mBuidDungeon();
			switch(this.aShape)
			{
				case Rectangle:
				{
					this.aShape = EShape.RoundRectangle;
				}break;
				case RoundRectangle:
				{
					this.aShape = EShape.Oval;
				}break;
				case Oval:
				{
					this.aShape = EShape.Rectangle;
				}break;
			}
		}		
	}
	
	public void mBuidDungeon()
	{
		if(this.aCells != null)
		{
			for(List<MapCell> vLine : this.aCells)
			{
				vLine.clear();
			}
			this.aCells.clear();
			this.aCells = null;
		}
		
		this.aCells = new ArrayList<List<MapCell>>();
		
		for(int vY = 0; vY < this.aLines; vY++)
		{
			List<MapCell> vLine = new ArrayList<MapCell>();
			for(int vX = 0; vX < this.aColumns; vX++)
			{
				vLine.add(new MapCell(vX, vY));
			}
			this.aCells.add(vLine);
		}
		
		List<MapCell> vActiveCells = new ArrayList<MapCell>();
		int vY = this.aRandom.nextInt(this.aCells.size());
		int vX = this.aRandom.nextInt(this.aCells.get(vY).size());
		this.aStartCell = this.aCells.get(vY).get(vX);
		vActiveCells.add(this.aStartCell);
		
		while(vActiveCells.size() < this.aCellsNumber)
		{
			int vRandom = this.aRandom.nextInt(vActiveCells.size());
			MapCell vCell = vActiveCells.get(vRandom);
			int vDirrection = this.aRandom.nextInt(4) + 1;
			switch(vDirrection)
			{
				case 1:
				{
					vX = (int) (vCell.mX());
					vY = (int) (vCell.mY() - 1);
					if
					(
						(vY > -1)
						&& 
						(vY < this.aCells.size())
					)
					{
						MapCell vAddCell = this.aCells.get(vY).get(vX);
						if( ( vCell.mDoors() & EDoors.Up.mValue() ) != EDoors.Up.mValue() )
						{
							if(!vAddCell.mIsOpen())
							{
								vActiveCells.add(vAddCell);
								vAddCell.mAddDoor(EDoors.Down);
								vCell.mAddDoor(EDoors.Up);
							}
						}
					}
				}break;
				case 2:
				{
					vX = (int) (vCell.mX() + 1);
					vY = (int) (vCell.mY());
					if
					(
						(vX > -1)
						&& 
						(vX < this.aCells.get(vY).size())
					)
					{
						MapCell vAddCell = this.aCells.get(vY).get(vX);
						if( ( vCell.mDoors() & EDoors.Right.mValue() ) != EDoors.Right.mValue() )
						{
							if(!vAddCell.mIsOpen())
							{
								vActiveCells.add(vAddCell);
								vAddCell.mAddDoor(EDoors.Left);
								vCell.mAddDoor(EDoors.Right);
							}
						}
					}
				}break;
				case 3:
				{
					vX = (int) (vCell.mX());
					vY = (int) (vCell.mY() + 1);
					if
					(
						(vY > - 1)
						&& 
						(vY < this.aCells.size())
					)
					{
						MapCell vAddCell = this.aCells.get(vY).get(vX);
						if( ( vCell.mDoors() & EDoors.Down.mValue() ) != EDoors.Down.mValue() )
						{
							if(!vAddCell.mIsOpen())
							{
								vActiveCells.add(vAddCell);
								vAddCell.mAddDoor(EDoors.Up);
								vCell.mAddDoor(EDoors.Down);
							}
						}
					}
				}break;
				case 4:
				{
					vX = (int) (vCell.mX() - 1);
					vY = (int) (vCell.mY());
					if
					(
						(vX > - 1)
						&& 
						(vX < this.aCells.get(vY).size())
					)
					{
						MapCell vAddCell = this.aCells.get(vY).get(vX);
						if( ( vAddCell.mDoors() & EDoors.Left.mValue() ) != EDoors.Left.mValue() )
						{
							if(!vAddCell.mIsOpen())
							{
								vActiveCells.add(vAddCell);
								vAddCell.mAddDoor(EDoors.Right);
								vCell.mAddDoor(EDoors.Left);
							}							
						}
					}
				}break;
			}
		}				
	}
	
	public void mDraw(GraphicsContext pGraphicsContext)
	{
		for(List<MapCell> vLine : this.aCells)
		{
			for(MapCell vCell : vLine)
			{
				if(vCell.equals(this.aStartCell))
				{
					pGraphicsContext.setFill(Color.rgb(0,255,0));
				}
				else
				{
					if(vCell.mIsOpen())
					{
						pGraphicsContext.setFill(Color.rgb(128, 128, 128));
					}
					else
					{
						pGraphicsContext.setFill(Color.rgb(16, 16, 16));
					}				
				}
				switch(this.aShape)
				{
					case Rectangle:
					{
						pGraphicsContext.fillRect(vCell.mX() * this.aWidth + vCell.mX() * this.aSpace + this.aSpace, vCell.mY() * this.aHeight + vCell.mY() * this.aSpace + this.aSpace, this.aWidth, this.aHeight);//, this.aWidth / 4, this.aHeight / 4 );
					}break;
					case RoundRectangle:
					{
						pGraphicsContext.fillRoundRect(vCell.mX() * this.aWidth + vCell.mX() * this.aSpace + this.aSpace, vCell.mY() * this.aHeight + vCell.mY() * this.aSpace + this.aSpace, this.aWidth, this.aHeight, this.aWidth / 4, this.aHeight / 4 );
					}break;
					case Oval:
					{
						pGraphicsContext.fillOval(vCell.mX() * this.aWidth + vCell.mX() * this.aSpace + this.aSpace, vCell.mY() * this.aHeight + vCell.mY() * this.aSpace + this.aSpace, this.aWidth, this.aHeight);//, this.aWidth / 4, this.aHeight / 4 );
					}break;
				}
								

				pGraphicsContext.setFill(Color.rgb(128, 128, 128));
				if((vCell.aDoors & EDoors.Up.mValue()) == EDoors.Up.mValue())
				{
					pGraphicsContext.fillRect(vCell.mX() * this.aWidth + vCell.mX() * this.aSpace + (this.aWidth / 2) + (this.aSpace / 2), vCell.mY() * this.aHeight + vCell.mY() * this.aSpace + 1, this.aSpace, this.aSpace);
				}
				if((vCell.aDoors & EDoors.Right.mValue()) == EDoors.Right.mValue())
				{
					pGraphicsContext.fillRect(vCell.mX() * this.aWidth + vCell.mX() * this.aSpace + this.aWidth + this.aSpace - 1, vCell.mY() * this.aHeight + vCell.mY() * this.aSpace + (this.aHeight / 2) + (this.aSpace / 2), this.aSpace, this.aSpace);
				}
				if((vCell.aDoors & EDoors.Down.mValue()) == EDoors.Down.mValue())
				{
					pGraphicsContext.fillRect(vCell.mX() * this.aWidth + vCell.mX() * this.aSpace + (this.aWidth / 2) + (this.aSpace / 2), vCell.mY() * this.aHeight + vCell.mY() * this.aSpace + this.aHeight + this.aSpace-1, this.aSpace, this.aSpace);
				}
				if((vCell.aDoors & EDoors.Left.mValue()) == EDoors.Left.mValue())
				{
					pGraphicsContext.fillRect(vCell.mX() * this.aWidth + vCell.mX() * this.aSpace + 1, vCell.mY() * this.aHeight + vCell.mY() * this.aSpace + (this.aHeight / 2) + (this.aSpace/2), this.aSpace, this.aSpace);
				}
			}
		}
	}
}
