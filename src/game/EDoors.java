package game;

public enum EDoors 
{
	None(0),
	Up(1),
	Right(2),
	Down(4),
	Left(8);
	
	private int aValue;
	
	EDoors(int pValue)
	{
		this.aValue = pValue;
	}
	
	public int mValue()
	{
		return this.aValue;
	}
}
