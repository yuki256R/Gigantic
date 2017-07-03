Growth Tool Development Reference
=====
開発者の方向けの参考資料として本ドキュメントをご参照ください。

YML構成の説明
---
Growth Toolでは下記2種類のYMLを使用しています。

* debug.yml<br />
Giganticプラグインの共通デバッグコンフィグです。<br />
ここにある`growthtool: no`を`growthtool: yes`とすることで、Growth Toolをデバッグモードで起動します。<br />
デバッグモードでは、コンソール及びプレイヤーログに各種デバッグメッセージを出力します。<br />
また、[getコマンド][Command]が有効化され、growthtool.ymlの上書きが無効になります。<br />
* growthtool.yml<br />
Growth Toolの設定ファイルです。内容については[YML Reference][YML]をご参照ください。<br />

growthtool.ymlの編集は、編集差分のメンテナンス性を考慮して現状は無効化しています。<br />
プラグインディレクトリでymlを変更しても、毎起動時ソースコード内のymlで上書きされます。<br />
ymlを変更したい場合はソースコードから変更してください。<br />
なお、デバッグモードではデバッグ目的によりymlは上書きされません。<br />

Growth Tool無効化方法
---
Growth Toolは、下記の手順により無効化可能です。

* GrowthTool.onEvent<br />
呼び出し直後に`return;`してください。<br />
* GrowthCommand.onCommand<br />
呼び出し直後に`return false;`してください。<br />

新規アイテム追加手順
---
作成するGrowth Toolの各設定値を、growthtool.ymlに追記してください。<br />
必要な設定値及び各値の意味合いは、[YML Reference][YML]をご参照ください。<br />

GrowthTool.GrowthToolTypeのenumに任意の定数と、実体クラスを設定することで有効化出来ます。<br />
例えばMEBIUSは下記の形式で設定してあります。<br />

`MEBIUS(Mebius.class),`

定数名にGrowth Toolの固有識別名を、コンストラクタ引数に実体クラスを指定してください。<br />
実体クラスはGrowthToolManagerを継承したクラスとする必要があります。<br />
ヘルメット型のリファレンスクラスとしてHelmetクラスを用意しています。<br />
更にMEBIUSは加えてTips処理を実装するため、独自クラスMebiusを実体クラスとしています。<br />

このように、独自クラスを実装することで固有処理を追加することが可能です。<br />

* 例えば・・・アイデア提案からfuture実現を考えてるけど手が回ってない機能<br />
    * MEBIUS Lv30を両手に持って右クリックすると、RE:MEBIUS（仮） Lv1になる。<br />
    * RE:MEBIUS Lv50を両手に持って右クリックすると、RE:MEBIUS Lv51になる。<br />
    * RE:MEBIUS Lv99で内部的には耐久値を操作、見た目が変わる。<br />

ソースコード内キーワード [移行用] について
---
前プラグインのMEBIUSを今回のプラグイン用に変換するための暫定的な処理です。<br />
将来的には不要になる処理なので、十分な引継ぎ期間の後に落としても構いません。<br />
大きな影響はない為、移行用処理は残したままでも構いません。<br />

* 影響点<br />
MCID管理だった前MEBIUSをUUID管理へ変更する処理を入れています。<br />
この処理を悪用した場合、Growth Toolの所有者変更が可能になります。<br />

Future
---
下記処理が未実装です。

* 新規プレイヤーへの配布処理<br />
前プラグインでは新規プレイヤーにはMEBIUSを1つ配布していた。<br />

* スライムのonEntityDeath判定<br />
スライム大、中は現状ではkill判定されないはず。<br />

* アイテム取得時の固有メッセージ<br />
必要であれば、前プラグインに存在した、ドロップアイテムから直接お言葉を頂く処理の実装。<br />

* レベルアップ時の固有メッセージ<br />
前プラグインはカスタムメッセージ1をお喋り頂いていたが、必要であればその処理の実装。<br />

* 金床使用の禁止処理<br />
NBT変更による対策の有識者がいた記憶<br />

* 新スキルと併用した確認<br />
新しいスキルに対しての確率設定やbreakイベントの発生量、経験値量等が未確認。<br />

* 全体的にデバッグが不十分、特に前プラグインのMEBIUSからの引継ぎ<br />
簡単なデバッグは行っているが、引継ぎは一切デバッグ出来ていない。<br />
引継ぎのデバッグが不十分な場合、取り返しがつかなくなる可能性あり。<br />

[Command]: ./CommandReference.md
[YML]: ./YmlReference.md
