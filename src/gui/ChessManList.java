/*
ID: lazydom1
LANG: JAVA
TASK: ChessMan.java
Created on: 2012-2-19-����11:34:11
Author: lazydomino@163.com(pisces)
*/

package gui;

import java.util.ArrayList;

/*
 * �����������̵��ࡣÿ�����Ӷ�����һ��ArrayList���棬����ArrayList�Ϳ����ҵ���������λ�á�
 */
public class ChessManList {
	
	
	public ChessManList()
	{
		
	}
	//var
	
	private ArrayList<ChessMan> list = new ArrayList<ChessMan>();
	private boolean[][] black_white = new boolean[8][8];
	private boolean[][] have_ChessMan = new boolean[8][8]; 
	int numBlack = 0;
	int numWhite = 0;
	
	public void add(int x,int y, boolean black)//the x and y must be start from 1,1
	{
		x -= 1;
		y -= 1;
		if(have_ChessMan[x][y]) return ;
		list.add(new ChessMan(x, y, black));
		black_white[x][y] = black;
		have_ChessMan[x][y] = true; 
		if(black) numBlack++;else numWhite++;
	}
	
	public void turn(int x, int y)//the x and y must be start from 1,1
	{
		x -= 1;
		y -= 1;
		if(black_white[x][y])
		{
			numBlack--;
			numWhite++;
		}else
		{
			numWhite--;
			numBlack++;
		}
		black_white[x][y] = !black_white[x][y];
		for(int i = 0 ;i <list.size();i++)
			if(list.get(i).getX() == x+1 && list.get(i).getY() == y+1)
			{
				list.get(i).setColor();
			}
	}
	
	public int getSize()
	{
		return list.size();
	}
	
	public ChessMan getChessMan(int index)
	{
		return list.get(index);
	}
	
	public boolean getColor(int x,int y)//the x and y must be start from 1,1
	{
		return black_white[x -1][y -1];
	}
	
	public boolean havaChessman(int x, int y)//the x and y must be start from 1,1
	{
		return have_ChessMan[x - 1][y -1];
	}
	
	public int getBlackNum()
	{
		return numBlack;
	}
	
	public int getwhiteNum()
	{
		return numWhite;
	}
	
	public boolean isBlackWin()
	{
		return numBlack > numWhite ? true:false;
	}
	
	public void clear()
	{
		list.clear();
		for(int i = 0; i<8;i++)
			for(int j = 0; j<8;j++)
			{
				black_white[i][j] = false;
				have_ChessMan[i][j] = false;
			}
	}
	

}

/*
 * �������ӵ��࣬��Ϊ������ 8*8 �ģ�����Ϊ�˷���ֱ���� i��j���꣬���� [1,2]����C2�ϵ����ӡ� 
 */
class ChessMan
{	
	public ChessMan(int x,int y, boolean black)
	{
		this.x = x ;
		this.y = y ;
		this.black = black;
	}
	
	public boolean isBlack()
	{
		return black;
	}
	public int getX()
	{
		return this.x +1;
	}
	public int getY()
	{
		return this.y+1;
	}
	public void setColor()
	{
		this.black = !this.black;
	}
	
	//var
	private int x, y;
	private boolean black;//��¼������ɫ
}