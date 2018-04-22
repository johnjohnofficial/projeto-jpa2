package br.com.caelum;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class TestaPool {

	public static void main(String[] args) throws PropertyVetoException, SQLException {
		
		ComboPooledDataSource dataSource = (ComboPooledDataSource) new JpaConfigurator().getDataSource();
		
		for (int i=0; i < 10; i++) {
			dataSource.getConnection();
			
			System.out.println("Conexão existente(s): " + dataSource.getNumConnections());
			System.out.println("Conexão ocupada(s): " + dataSource.getNumBusyConnections());
			System.out.println("Conexão ociosa(s): " + dataSource.getNumIdleConnections());
			System.out.println();
		}

	}

}
