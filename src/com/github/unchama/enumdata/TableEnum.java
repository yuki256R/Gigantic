package com.github.unchama.enumdata;




public enum TableEnum {
	PLAYERDATA("playerdata"),
	GACHADATA("gachadata"),
	DONATEDATA("donatedata"),
	MSGACHADATA("msgachadata"),
	;

	private String tablename;

	/**コンストラクタ
	 *
	 * @param tablename
	 */
	TableEnum(String tablename){
		this.tablename = tablename;
	}

	/**sqlのテーブル名を取得する
	 *
	 * @return
	 */
	public String getTableName(){
		return this.tablename;
	}

	/**sql初期処理時にロードするかどうか
	 *
	 * @return
	 */
	public Boolean getInitLoadFlag(){
		switch(this){
		case DONATEDATA:
			break;
		case GACHADATA:
			break;
		case MSGACHADATA:
			break;
		case PLAYERDATA:
			break;
		default:
			break;

		}
		return null;
	}
	public String getUniqueCreateCommand(){
		String command = "";
		switch(this){
		case PLAYERDATA:
			command =
			"(uuid varchar(128) unique)";
			break;
		case DONATEDATA:
			break;
		case GACHADATA:
			break;
		case MSGACHADATA:
			break;
		default:
			break;
		}
		return command;
	}

	public String getColumnDataCommand() {
		String command = "";
		switch(this){
		case PLAYERDATA:
			command =
					"add column if not exists effectflag tinyint default 0" +
					",add column if not exists minestackflag boolean default true" +
					",add column if not exists messageflag boolean default false" +
					",add column if not exists effectnum int default 0" +
					",add column if not exists gachapoint int default 0" +
					",add column if not exists gachaflag boolean default true" +
					",add column if not exists level int default 1" +
					",add column if not exists numofsorryforbug int default 0" +
					",add column if not exists inventory blob default null" +
					",add column if not exists rgnum int default 0" +
					",add column if not exists totalbreaknum int default 0" +
					",add column if not exists lastquit datetime default null" +
					",add column if not exists displayTypeLv boolean default true" +
					",add column if not exists displayTitleNo int default 0" +
					",add column if not exists TitleFlags text default null" ;
			break;
		case DONATEDATA:
			break;
		case GACHADATA:
			break;
		case MSGACHADATA:
			break;
		default:
			break;
		}
		return command;
	}
}
