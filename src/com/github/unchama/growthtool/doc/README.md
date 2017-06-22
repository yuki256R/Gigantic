Growth Tool Release Note
=====
Growth Toolはプレイヤーの整地と共に成長していくツールです。<br />
Growth Toolはアイテムレベルを持ち、アイテムレベルの上昇と共に外見やエンチャントが変化します。<br />
また被ダメージや時間経過等のイベントにより、プレイヤーに対して話しかけてきます。<br />

[Growth Tool Command Reference](./COMMANDREFERENCE.md)
---
Growth Toolのコマンドについては本ドキュメントをご参照ください。

License
---
Copyright &copy; 2017 ***[unchama]*** by [ギガンティック☆整地鯖]

Licensed under [Gigantic plugin](https://github.com/unchama/Gigantic/)

ChangeLog
---
2017.6.22 新規作成 by [CrossHearts](https://github.com/CrossHearts/)



ymlの説明
debug.yml、growthtool.yml
アイテムの追加にはソースコードの編集が必須の為、メンテナンス性を考慮してプラグインディレクトリ内のymlは毎回上書きしている。
DebugModeをtrueにした場合のみ上書きしないため、現状はデバッグ時の活用を目的としている。





新規アイテム追加方法



オリジナル処理追加方法



キーワード [移行用] について



TODO:
新規プレイヤーへの配布処理
金床で命名を出来ないように
スライムを倒した時の判定
アイテム取得時の固有メッセージが無い

全体的にデバッグが不十分、特に旧メビウスからの引継ぎに対してのデバッグが出来ていない
→seichiassistでmebiusのdebugmodeをonにして取っておいて、seichiassistからgiganticに入れ替える方法が取れそう、Lv1,15,39くらいのケースで試したい
引継ぎのアイテムに対しても上書き処理を行うため、取返しのつかないことになる可能性がある。

