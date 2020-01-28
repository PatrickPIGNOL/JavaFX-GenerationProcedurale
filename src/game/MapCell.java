package game;

public class MapCell 
{	
	double aX;
	double aY;
	int aDoors;
	
	public MapCell(double pX, double pY)
	{
		this.aX = pX;
		this.aY = pY;
		this.aDoors = EDoors.None.mValue();
	}
	
	public int mDoors()
	{
		return this.aDoors;
	}
	
	public void mDoors(int pDoors)
	{
		this.aDoors = pDoors;
	}
	
	public void mAddDoor(EDoors pDoor)
	{	
		if( (this.aDoors & pDoor.mValue()) == 0 )
		{
			this.aDoors += pDoor.mValue();
		}
	}
	
	public void mSubstractDoor(EDoors pDoor)
	{
		if((this.aDoors & pDoor.mValue()) == pDoor.mValue())
		{
			this.aDoors -= pDoor.mValue();
		}
	}
	
	public boolean mIsOpen()
	{
		return this.aDoors != EDoors.None.mValue();
	}
	
	public double mX()
	{
		return this.aX;
	}
	
	public double mY()
	{
		return this.aY;
	}
}
