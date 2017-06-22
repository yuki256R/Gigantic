Growth Tool Command Reference
=====
Growth Toolのコマンドについては本ドキュメントをご参照ください。

Permission
---
* ```gigantic.growth```

デフォルトでは全ユーザが使用可能です。

Commands
---
Growth Toolに対してのコマンドは、下記3種類のいずれかで実行可能です。

* `/giganticgrowth`
* `/growth`
* `/grw`

Growth Toolでは下記コマンドを用意しています。

---

* 命名コマンド<br />
`/grw <GrowthToolName> name <NewName>`<br />
`/grw <GrowthToolName> name`<br />

    * `<GrowthToolName>` : 各Growth Toolの固有名称<br />
    * `<NewName>` : 命名したい新しい名前<br />

    装備中のGrowth Toolに対して名前を設定します。<br />
    NewNameに何も設定しない場合、命名無しに戻ります。<br />
    NewNameは最大10文字で、`<`, `>`は使用出来ません。条件に反した場合は削られます。<br />

---

* 愛称設定コマンド<br />
`/grw <GrowthToolName> call`<br />
`/grw <GrowthToolName> call <NickName>`<br />

    * `<GrowthToolName>` : 各Growth Toolの固有名称<br />
    * `<NickName>` : 設定したい愛称<br />

    装備中のGrowth Toolがプレイヤーを呼ぶ際の愛称を設定します。<br />
    NickNameに何も設定しない場合、愛称無しに戻ります。<br />
    NickNameは最大10文字で、`<`, `>`は使用出来ません。条件に反した場合は削られます。<br />

---

* （デバッグ限定）Growth Tool入手コマンド<br />
`/grw <GrowthToolName> get`<br />

    * `<GrowthToolName>` : 各Growth Toolの固有名称<br />

    指定のGrowth Toolを初期状態で入手します。<br />
    本コマンドは、サーバ側の`debug.yml`内で`growthtool: yes`が設定されていなければ無効化されています。<br />
