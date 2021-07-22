package com.crazymoney.scanningwallet.database.table;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "wallet")
public class Wallet implements Serializable, Comparable<Wallet> {

	public static class Fields {
		public static final String ID = "id";
		public static final String NAME = "name";
		public static final String ADDRESS = "address";
		public static final String NETWORK = "network";
	}

	@DatabaseField(allowGeneratedIdInsert = true, canBeNull = false, columnName = Fields.ID, generatedId = true)
	private long id;

	@DatabaseField(columnName = Fields.NAME)
	private String name;

	@DatabaseField(columnName = Fields.ADDRESS)
	private String address;

	@DatabaseField(columnName = Fields.NETWORK)
	private int network;

	public Wallet() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setNetwork(int network) {
		this.network = network;
	}

	public String getNetworkName() {
		switch (this.network) {
			case 1:
				return "Binance Smart Chain";
			case 2:
				return "Fantom";
			case 3:
				return "Avalance";
			case 4:
				return "Polygon / Matic";
			case 5:
				return "Moonbeam";
			default:
				return "Ethereum";
		}
	}

	public int getChainId() {
		switch (this.network) {
			case 1:
				return 56;
			case 2:
				return 250;
			case 3:
				return 43114;
			case 4:
				return 137;
			case 5:
				return 1287;
			default:
				return 1;
		}
	}

	@Override
	public String toString() {
		return "Wallet{" +
				"id=" + id +
				", name='" + name + '\'' +
				", address='" + address + '\'' +
				", network=" + network +
				'}';
	}

	@Override
	public int compareTo(Wallet o) {
		return 0;
	}

	public enum Network {
		ETH,
		BSC,
		FANTOM,
		AVALANCHE,
		MATIC,
		MOONBEAM;

		static Network fromInt(int value) {
			return Network.values()[value];
		}
	}
}
