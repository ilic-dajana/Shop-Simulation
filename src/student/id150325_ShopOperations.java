package student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import jdbc2.DB;
import operations.ShopOperations;

public class id150325_ShopOperations implements ShopOperations {

	@Override
	public int createShop(String name, String cityName)  {
		String insert = "INSERT INTO Prodavnica(popust, novac, IdGrad, Ime) VALUES(?,?,?,?)";
		Connection con = DB.getInstance().getConnection();
		try {
			String check = "Select MAX(IdGrad) AS IDGr FROM Grad WHERE Ime=?";
	
			PreparedStatement ps = con.prepareStatement(check);
			ps.setString(1, cityName);
			ResultSet rs = ps.executeQuery();
			
			if(!rs.next())
				return -1;	
			Integer idGrad = rs.getInt("IDGr");	
			ps = con.prepareStatement(insert);
			ps.setInt(1, 0);
			ps.setInt(2, 0);
			ps.setInt(3, idGrad);
			ps.setString(4, name);
			
			ps.executeUpdate();
			
			PreparedStatement select = con.prepareStatement("SELECT MAX(IdProdavnica) AS Max FROM Prodavnica WHERE Ime = ?");				
			select.setString(1, name);
			rs = select.executeQuery();
			rs.next();
			Integer id = rs.getInt("Max");
			
			return id>0 ? id : -1;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public int setCity(int shopId, String cityName) {
		String update = "UPDATE Prodavnica SET IdGrad = ? WHERE IdProdavnica = ?";
		Connection con = DB.getInstance().getConnection();
		
		String check = "Select MAX(IdGrad) AS IDGr FROM Grad WHERE Ime=?";
		
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(check);
			ps.setString(1, cityName);			
			ResultSet rs = ps.executeQuery();
			
			if(!rs.next())
				return -1;	
			
			Integer idGrad = rs.getInt("IDGr");	
			
			check = "Select * AS ID FROM Prodavnica WHERE IdProdavnica = ?";
			ps = con.prepareStatement(check);
			ps.setInt(1, shopId);			
			ResultSet rs1 = ps.executeQuery();
			
			if(!rs1.next())
				return -1;
			
			ps = con.prepareStatement(update);
			ps.setInt(1, idGrad);
			ps.setInt(2, shopId);
			ps.executeUpdate();
			
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public int getCity(int shopId) {
		String check = "Select MAX(IdGrad) AS IDGr FROM Prodavnica WHERE IdProdavnica=?";
		Connection con = DB.getInstance().getConnection();
		
			PreparedStatement ps;
			try {
				ps = con.prepareStatement(check);
			
				ps.setInt(1, shopId);
				ResultSet rs = ps.executeQuery();
				
				if(!rs.next())
					return -1;	
				Integer idGrad = rs.getInt("IDGr");	
				return idGrad;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return -1;
	}

	@Override
	public int setDiscount(int shopId, int discountPercentage) {
		String update = "UPDATE Prodavnica SET popust = ? WHERE IdProdavnica = ?";
		Connection con = DB.getInstance().getConnection();		
		
		PreparedStatement ps;
		try {
			
			String check = "Select * AS ID FROM Prodavnica WHERE IdProdavnica = ?";
			ps = con.prepareStatement(check);
			ps.setInt(1, shopId);			
			ResultSet rs1 = ps.executeQuery();
			
			if(!rs1.next())
				return -1;
			
			ps = con.prepareStatement(update);
			ps.setInt(1, discountPercentage);
			ps.setInt(2, shopId);
			ps.executeUpdate();
			
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public int increaseArticleCount(int articleId, int increment) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getArticleCount(int shopId, int articleId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Integer> getArticles(int shopId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDiscount(int shopId) {
		Connection con = DB.getInstance().getConnection();
		
		String check = "Select popust AS p FROM Prodavnica WHERE IdProdavnica = ?";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(check);
			ps.setInt(1, shopId);			
			ResultSet rs1 = ps.executeQuery();
			
			if(!rs1.next())
				return -1;
			
			return rs1.getInt("p");
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
	}

}
