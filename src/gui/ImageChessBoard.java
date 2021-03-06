/*
ID: lazydom1
LANG: JAVA
TASK: ImageJPanel.java
Created on: 2012-2-19-上午10:17:59
Author: lazydomino@163.com(pisces)
*/

package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import core.ChessMan;
import core.ChessManList;


/**
 * @author will
 * 这个是否则绘制房间（棋盘）主界面的类
 * 重载了JPanel 的 paint 方法，把背景图片画上。
 * 绘制黑色分割线棋盘。
 * @see javax.swing.JComponent#paint(java.awt.Graphics)
 */
public class ImageChessBoard extends JComponent{

	public ImageChessBoard()
	{
		super();
		this.setLayout(null);	
		setImage(2,2);
		setTime("00","00");
	}


	public void update(ChessManList list, ChessManList canPlace)
	{
		this.list = list;
		this.canPlace = canPlace;
		//System.out.println("listsize="+list.getSize());
	}

	public void setImage(int u1,int u2)
	{
		this.u1 = new ImageIcon("res"+File.separator+u1+".png");
		this.u2 = new ImageIcon("res"+File.separator+u2+".png");
	}
	
	public void setTime(String a,String b)
	{
		time1 = "剩余时间："+a+"S";
		time2 = "剩余时间："+b+"S";
	}
	
	
	
	public void setTime1(int time1) {
		this.time1 = "剩余时间：";
		if(time1<10) this.time1+="0";
		this.time1 += time1;
	}
	public void setTime2(int time2) {
		this.time2 = "剩余时间：";
		if(time2<10) this.time2+="0";
		this.time2 += time2;
	}
	public void setMe(int me) {
		this.me = me;
	}

	public void paintComponent(Graphics g)
	{


		//System.out.println("paintComponent");
		super.paintComponent(g);
		ImageIcon img = new ImageIcon("res"+File.separator+"chess_background.png"); 
		g.drawImage(img.getImage(), 0, 0, img.getIconWidth(), img.getIconHeight(), this);

		ImageIcon chessBoard = new ImageIcon("res"+File.separator+"chess_board.png");
		g.drawImage(chessBoard.getImage(), 160, 120, chessBoard.getIconWidth(), chessBoard.getIconHeight(), this);

		g.drawImage(u1.getImage(), 18, 187, 119, 160,this);
		g.drawImage(u2.getImage(), 670, 187, 119, 160,this);
		
		
		ImageIcon jiantou = new ImageIcon("res"+File.separator+"jiantou.jpg");
				
		if(me==1)
		{
			g.drawImage(jiantou.getImage(),65,150,30,30,this);
		}else
			if(me==2)
		{
			g.drawImage(jiantou.getImage(),720,150,30,30,this);
		}
		
		
		g.setFont(new Font("Seril",Font.BOLD,16));
		g.setColor(Color.cyan);
		g.drawString(time1, 18, 370);
		g.drawString(time2, 670, 370);
		
		g.setColor(Color.BLACK);
		
		for(int i = 0; i<8;i++)
			for(int j = 0; j<8;j++)
			{
				g.drawRect(startx + i * 56, starty + j * 56, 56, 56);
			}

		ImageIcon blackimg = new ImageIcon("res"+File.separator+"black_ball.png");
		ImageIcon whiteimg = new ImageIcon("res"+File.separator+"white_ball.png");

		
		
		int count = 0;
		for(int i = 0; i< list.getSize();i++)
		{


			ChessMan chessMan = list.getChessMan(i);
			if(chessMan.isBlack())
			{
				count ++;
				g.drawImage(blackimg.getImage(), startx + (chessMan.getX()-1)*56 + 5, starty + (chessMan.getY()-1)*56+5, 46, 46, this);
			}
			else
				g.drawImage(whiteimg.getImage(), startx + (chessMan.getX()-1)*56 + 5, starty + (chessMan.getY()-1)*56+5, 46, 46, this);
		}

		for(int i = 0; i< canPlace.getSize();i++)
		{

			ChessMan chessMan = canPlace.getChessMan(i);
			g.drawRect(startx + (chessMan.getX()-1)*56 + 5, starty + (chessMan.getY()-1) *56 + 5, 46, 46);
		}
		//System.out.println("paint:"+count);
		
		
		
		
	}

	//var
	int startx = 178, starty = 140;
	private ChessManList list, canPlace;
	private ImageIcon u1,u2;
	private String time1,time2;
	private int me;
	
}