/*
ID: lazydom1
LANG: JAVA
TASK: ChessFrame.java
Created on: 2012-2-18-����7:57:13
Author: lazydomino@163.com(pisces)
*/

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import core.ChessMan;
import core.ChessManList;

public class ChessFrame extends JFrame{

	public ChessFrame()
	{
		super();
		
		this.setTitle("�ڰ���");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon ico = new ImageIcon("res\\ico.png");
		this.setIconImage(ico.getImage());
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.setMinimumSize(new Dimension(1024, 730));
		this.setMaximumSize(new Dimension(1024, 730));
		this.setResizable(false);
		
		addPanel();
		
	}
	/*
	 * add the mainPanel and the rightPanel include the chatPanel and the players' information panel.
	 */
	private void addPanel()
	{
		

		chessManList = new ChessManList();
		chessManList.add(4, 4, false);
		chessManList.add(4, 5, true);
		chessManList.add(5, 4, true);
		chessManList.add(5, 5, false);
		black = true;
		
		canPlace = new ChessManList();
		canPlace.add(3, 4, true);
		canPlace.add(4, 3, true);
		canPlace.add(6, 5, true);
		canPlace.add(5, 6, true);
		
		
		mainPanel = new ImageChessBoard();
		mainPanel.update(chessManList, canPlace);
		undoButton = new JButton("����");
		undoButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(now_step > 0)
				{
					
					chessManList.clear();
					canPlace.clear();
					
					if(now_step == 1)
					{
						
						chessManList.add(4, 4, false);
						chessManList.add(4, 5, true);
						chessManList.add(5, 4, true);
						chessManList.add(5, 5, false);
						
						canPlace.add(3, 4, true);
						canPlace.add(4, 3, true);
						canPlace.add(6, 5, true);
						canPlace.add(5, 6, true);
						black = true;
						
					}else
					{
						chessManList = new ChessManList();
						
						if(undoChessManList[--now_step].black)
							black = true;
						else
							black = false;
						for(ChessMan c : undoChessManList[now_step].getList())
						{
							chessManList.add(c);
							
						}
					
						for(int i = 0; i< chessManList.getSize();i++)
						{
							if(chessManList.getChessMan(i).isBlack() != black) 
							{
								findCanPlace(chessManList.getChessMan(i));
							}
						}
						
						if(canPlace.getSize() == 0)
						{
							black = !black;
							System.out.println(!black+" is skip!");
							for(int i = 0; i< chessManList.getSize();i++)
							{
								if(chessManList.getChessMan(i).isBlack() != black) 
								{
									findCanPlace(chessManList.getChessMan(i));
								}
							}
						}
						
						
						black = !black;
						
					}
					
					mainPanel.update(chessManList, canPlace);
					
					//System.out.println(chessManList.isBlack(4, 4));
					//System.out.println("frame_size="+chessManList.getSize());
					mainPanel.repaint();
					//now_step--;
				}			
				
			}
		});
		loseButton = new JButton("����");
		saveButton = new JButton("����");
		loadButton = new JButton("����");
		
		JPanel downPanel = new JPanel();
		
		downPanel.add(undoButton);
		downPanel.add(loseButton);
		downPanel.add(saveButton);
		downPanel.add(loadButton);
		downPanel.setBounds(260, 630,270, 36);
		mainPanel.add(downPanel);
		this.addMouseListener(new MouseAction());
		this.add(mainPanel);
		
		
		rightPanel = new RightJPanel();
		this.add(rightPanel, BorderLayout.EAST);
		
	}
	
	
	/*
	 * ������Է������ӵĵط���
	 */
	private void findCanPlace(ChessMan chessMan)
	{
		final int[] dx = {0,-1,-1,-1,0,1,1,1};
		final int[] dy = {-1,-1,0,1,1,1,0,-1};
		boolean can = false;
		int x = chessMan.getX();
		int y = chessMan.getY();
		
		for(int i = 0; i<8;i++)
		{
			can = false;
			x = chessMan.getX();
			y = chessMan.getY();
			//System.out.println("start= "+x+" "+y);
			while(x +dx[i] >= 1 && y +dy[i] >= 1 && x +dx[i]<=8 && y+dy[i]<=8)
			{
				if(chessManList.havaChessman(x + dx[i], y + dy[i]) && chessManList.isBlack(x + dx[i], y + dy[i]) != chessMan.isBlack())
				{
					x = x + dx[i];
					y = y + dy[i];
					can = true;
				//	System.out.println(x+" "+y);
				}else
				{
					break;
				}
			}
			if(x + dx[i] >= 1 && y + dy[i] >= 1 && x + dx[i] <=8 && y + dy[i]<=8 && !chessManList.havaChessman(x + dx[i], y + dy[i]) && can)
			{
			//	System.out.println("find= "+(x + dx[i])+" "+(y + dy[i]));
				canPlace.add(x + dx[i], y + dy[i], chessMan.isBlack());
			}
		}	
	}
	
	
	/*
	 * ����������Ӻ�Ӧ�÷�ת�����ӡ�
	 */
	private ChessManList turnChessMan(ChessMan chessMan)
	{
		final int[] dx = {0,-1,-1,-1,0,1,1,1};
		final int[] dy = {-1,-1,0,1,1,1,0,-1};
		boolean can = false;
		int x = chessMan.getX();
		int y = chessMan.getY();
		
		
		ChessManList newList = chessManList;
		ChessManList shouldTurn = new ChessManList();
		
		for(int l = 1 ;l<=8;l++)
			for(int m = 1; m<=8;m++)
				if(chessManList.havaChessman(l, m) && chessManList.isBlack(l, m) == chessMan.isBlack())
				
				for(int i = 0; i<8;i++)
				{
					x = l;
					y = m; 
					shouldTurn.clear();
					can = false;
				
					if(x == chessMan.getX() && y == chessMan.getY()) break;
					
					while(x +dx[i] >= 1 && y +dy[i] >= 1 && x +dx[i]<=8 && y+dy[i]<=8)
					{
						if(chessManList.havaChessman(x + dx[i], y + dy[i]) && chessManList.isBlack(x + dx[i], y + dy[i]) != chessMan.isBlack())
						{
							x = x + dx[i];
							y = y + dy[i];
							can = true;
							shouldTurn.add(x, y, chessMan.isBlack());
							//System.out.println("shouldTurn:"+x+" "+y);
						}else
						{
							//System.out.println("break:"+x+" "+y);
							break;
						}
					}
					if((x + dx[i])==chessMan.getX() && (y + dy[i]) == chessMan.getY() && can)
					{
						for(int k = 0 ;k < shouldTurn.getSize();k++)
						{
							//System.out.println("turned:"+shouldTurn.getChessMan(k).getX()+" "+shouldTurn.getChessMan(k).getY());
							newList.turn(shouldTurn.getChessMan(k).getX(), shouldTurn.getChessMan(k).getY());
						}
					}
					
				}
				
		return newList;
		
		
		
	}
	
	
	
	public void finish()
	{
		System.out.println("win="+chessManList.isBlackWin());
	}
	
	
	//var
	private JPanel  rightPanel;
	private ImageChessBoard mainPanel;
	private ChessManList chessManList, canPlace;
	private ChessManList[] undoChessManList = new ChessManList[65];
	private JButton loseButton, undoButton, saveButton, loadButton;
	private int startx = 181, starty = 165;
	private int mouseX,mouseY;
	private boolean black;
	private int count = 0;
	private int now_step = 0;
	
	
	class MouseAction extends MouseAdapter 
	{
		public void mousePressed(MouseEvent event)
		{
			
			mouseX = event.getX();
			mouseY = event.getY();
			
			
			
			int x = mouseX;
			int y = mouseY;
			
			//System.out.println(x+" "+y);
			if(x <= startx || y <= starty || x >= 628 || y >=	612) return ;
			
			
			x -= startx;
			y -= starty;
			
			int pos_x = 1;
			int pos_y = 1;
			
			pos_x += x / 56;
			pos_y += y / 56;
			
			
			
			if(chessManList.havaChessman(pos_x, pos_y) || !canPlace.havaChessman(pos_x, pos_y)) return ;
			
			chessManList = turnChessMan(new ChessMan(pos_x, pos_y, black));
			
			chessManList.add(pos_x, pos_y, black);
			mainPanel.update(chessManList, canPlace);
			//System.out.println(pos_x+" "+pos_y);
			
			
			
			canPlace.clear();
			
			for(int i = 0; i< chessManList.getSize();i++)
			{
				if(chessManList.getChessMan(i).isBlack() != black) 
				{
					findCanPlace(chessManList.getChessMan(i));
				}
			}
		
			
			
			if(chessManList.getSize() == 64)
				finish();
			
			if(canPlace.getSize() != 0)
			{
				black = !black;
				count = 0;
			}else
			{
				count ++;
				System.out.println(!black+"  is skip!");
				System.out.println("count="+count);
				for(int i = 0; i< chessManList.getSize();i++)
				{
					if(chessManList.getChessMan(i).isBlack() == black)
					{
						findCanPlace(chessManList.getChessMan(i));
					}
				}
				if(canPlace.getSize() ==0)
					count ++;
				if(count ==2) finish();
				
				
			}
			
			try {
				undoChessManList[++now_step] = new ChessManList();
				for(ChessMan c : chessManList.getList())
				{
					undoChessManList[now_step].add(c.clone());
				}
				
				if(!black)
					undoChessManList[now_step].black = true;
				else
					undoChessManList[now_step].black = false;
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//System.out.println(black);
			
			mainPanel.repaint();
			
		}
	}
}
