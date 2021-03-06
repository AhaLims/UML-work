package cdd.desk.model.game;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cdd.desk.model.PlayGameCallBack;
import cdd.desk.model.card.Card;
import cdd.desk.model.card.CardColor;
import cdd.desk.model.card.CardsManager;
import cdd.desk.model.card.PairCardsGroup;
import cdd.desk.model.card.deliveredCardsGroup;
import cdd.desk.model.card.handCardsGroup;
import cdd.desk.model.role.Player;
import cdd.desk.model.role.Robot;
import cdd.desk.model.role.Role;
import cdd.tool.DbCallBack;
import cdd.tool.PlayerRepo;


//TODO 设置一个常量 玩家index = 0 可读性更强

public class Game{

	private PairCardsGroup AllCards;
	private Scorer scorer;
	private deliveredCardsGroup LatestCards;//最新的 出在牌桌上面的牌
	private int currentTurn;
	public int firstTurn;//其实不应该public的....后面再改吧
	private int turnTime;//轮数
	private Role[] roles;
	private boolean[] IsLatestShow;//用来判断最新是否出了牌
	Context context;

    private static final int PlayID = 0;
	//TODO 也许应该传进玩家的名字....然后....
	public Game(Context context) {
		this.context = context;
		scorer = new Scorer();
		//nextTurn = new int[4];
		AllCards = PairCardsGroup.getPairOfCards();
		roles = new Role[4];
		IsLatestShow = new boolean[4];
		LatestCards = new deliveredCardsGroup();
		for(int i = 0;i < 4; i++) {
			if(i == 0)
				continue;
			else roles[i] = new Robot();
			IsLatestShow[i] = false;//一开始大家都没有出牌
		}
	}

	public Game(String name,Context context){
		this.context = context;
		scorer = new Scorer();
		AllCards = PairCardsGroup.getPairOfCards();
		roles = new Role[4];
		Player player = new Player(name);
		IsLatestShow = new boolean[4];
		LatestCards = new deliveredCardsGroup();
		for(int i = 0;i < 4; i++) {
			if(i == 0)
				roles[i] = player;
			else roles[i] = new Robot();
			IsLatestShow[i] = false;//一开始大家都没有出牌
		}
	}

	public boolean IsFirstHand(int index) {//用来判断是不是先手
		for(int i = 0;i < 4;i++) {
			if (i == index)continue;
			else {
				if(IsLatestShow[i] == true)return false;
			}
		}
		//也许应该设置 LatestCards = new deliveredCardsGroup();
		LatestCards = new deliveredCardsGroup();
		return true;
	}

	public void InitGame(PlayGameCallBack playGameCallBack) {
        //进行玩家牌的初始化
		LatestCards = new deliveredCardsGroup();
		turnTime = 1;
        for(int i = 0;i < 4; i++){
            roles[i].getHandCards().getCardsGroup().clear();//清空牌
			IsLatestShow[i] = false;//清空最近出牌标记
        }
        //进行牌桌的初始化
		List<Card> noCard = new ArrayList<Card>();//这个是用来清理牌桌的
		for(int i = 0;i < 4; i++){
        	if(i == 0)
				playGameCallBack.displayPlayerCards(noCard);
        	else
				playGameCallBack.displayRobotCards(	noCard,i);
		}
		AllCards.shuffleCards();//洗牌
		licensingCards();//发牌

        playGameCallBack.displayPlayerHandCards(roles[0].getHandCards().getCardsGroup());//回调
		for(int i = 1;i < 4; i++){
			playGameCallBack.setRobotHandCard(roles[i].getHandCards().getCardsGroup(),i);//将机器人的牌传给前端
		}
		for(int i = 0;i < 4; i++){
			//在控制台打印所有人的牌
			System.out.print("当前第");
			System.out.print(i);
			System.out.println("个玩家的牌为:");
			roles[i].getHandCards().showDetail();
		}

		if(firstTurn == 0) {
			playGameCallBack.onNext(0);
			return;//第一个出牌的是玩家 则初始化工作完成
		}
		else//如果第一个出牌的不是玩家 那么应该先模拟机器人的出牌
		{
			deliveredCardsGroup deliveredCard;
			for(int i = firstTurn;i < 4;i++){//机器人出牌 并进行回调
				deliveredCard = roles[i].deliver(LatestCards);
                playGameCallBack.onNext(i % 4);
				if(deliveredCard.hasCards() == true)
				{
					roles[i].refreshCardsGroup(deliveredCard);//更新牌
					LatestCards = deliveredCard;//更新LatestCards
					IsLatestShow[i] = true;//机器人的状态为 出牌
				}
				playGameCallBack.displayRobotCards(deliveredCard.getCardsGroup(),i);
				playGameCallBack.setRobotHandCard( roles[i].getHandCards().getCardsGroup(),i);
				System.out.println("初始化的时候机器人的出牌局");
				playGameCallBack.onNext((i + 1) % 4);

			}
		}
	}
	//发牌
	private void licensingCards() {
		for(int i = 0;i < 52;i++)
		{
			Card card = AllCards.getCardByIndex(i);
			roles[i % 4].getSingleCards(card);
		}

		//找出拥有方块3 role的ID   方块3的weight为4
		for(int i = 0;i < 4;i++) {
			if(roles[i].findCard(4) == true)
			{
				currentTurn = i;
				firstTurn = i;
				break;
			}
		}
		//确定顺序 并对牌进行排序
		for(int i = 0;i < 4;i++) {
			roles[ (currentTurn + i) % 4].setNumber(i);
			roles[i].getHandCards().sort();
		}

	}

	//由presenter来调用这个函数
	//两个参数分别为:前端传来的牌的数组 以及presenter自己
	public void turn(List<Integer> list,PlayGameCallBack playGameCallBack) {
		deliveredCardsGroup currentCardsGroup;

		if (list == null) {//处理不出牌的事件
			System.out.println("不出牌事件");
			if (IsFirstHand(0)) {
				playGameCallBack.onCardsNotValid("先手必须出牌哦");
				return;
			} else {
				//合法的不出牌
				IsLatestShow[0] = false;//记录一下没有出牌
				//让三个机器人来出牌
                playGameCallBack.onRolePass(0);
				playGameCallBack.onNext(1);
				ThreeRobotsTurn(playGameCallBack);
				turnTime++;
			}
			return;
		}

		//下面处理出牌的事件

		currentCardsGroup = roles[0].selectCards(list);//获取player的牌组
		//第一轮且先手 并且选择了出牌 但是牌组中没有方块三
		if (turnTime == 1 && firstTurn == 0 && currentCardsGroup.canFindCard(3, CardColor.Diamond) == -1) {
			playGameCallBack.onCardsNotValid("第一轮必须有方块三哦");
			return;
		}
		else if(IsFirstHand(0)){
			System.out.println("这里是 非第一局的先手");
			if(LatestCards.hasCards() == false){
				System.out.println("??为什么lastestCard不为空？\n");
			}
			//出牌合法
			if(CardsManager.getCardsManager().isPermissible(LatestCards, currentCardsGroup, playGameCallBack))
			{
				playGameCallBack.displayPlayerCards(currentCardsGroup.getCardsGroup());

				LatestCards = currentCardsGroup;//把当前玩家出的牌设置为最近的牌
				roles[0].refreshCardsGroup(currentCardsGroup);//更新牌
				playGameCallBack.displayPlayerHandCards(roles[0].getHandCards().getCardsGroup());//回调
				IsLatestShow[0] = true;//记录一下出了牌
				turnTime++;
				if (roles[0].win() == true) {
					gameEnd(0,playGameCallBack);
					return;
				}
				//机器人出牌 并进行回调
				playGameCallBack.onNext(1);
				ThreeRobotsTurn(playGameCallBack);
				return;
			}
		}
		else if (CardsManager.getCardsManager().isPermissible(LatestCards, currentCardsGroup, playGameCallBack)){//先手 且 牌组中有方块三

			LatestCards = currentCardsGroup;//把当前玩家出的牌设置为最近的牌
			playGameCallBack.displayPlayerCards(currentCardsGroup.getCardsGroup());
			roles[0].refreshCardsGroup(currentCardsGroup);//更新牌
			playGameCallBack.displayPlayerHandCards(roles[0].getHandCards().getCardsGroup());//回调
			IsLatestShow[0] = true;//记录一下出了牌
			turnTime++;
			if (roles[0].win() == true) {
				gameEnd(0,playGameCallBack);
				return;
			}
			//机器人出牌 并进行回调
			playGameCallBack.onNext(1);
			ThreeRobotsTurn(playGameCallBack);
			return;
		}
		//不是第一轮的先手或者后手
		//合法的出牌
		if (CardsManager.getCardsManager().isPermissible(LatestCards, currentCardsGroup, playGameCallBack)) {

			LatestCards = currentCardsGroup;//把当前玩家出的牌设置为最近的牌
			//通知presenter 并更新玩家的牌
			playGameCallBack.displayPlayerCards(currentCardsGroup.getCardsGroup());
			roles[0].refreshCardsGroup(currentCardsGroup);//更新牌
			playGameCallBack.displayPlayerHandCards(roles[0].getHandCards().getCardsGroup());//回调
			IsLatestShow[0] = true;//记录一下出了牌
			//System.out.println("玩家的出牌局");

			//System.out.println("");
			turnTime++;

			//游戏结束了  需要进行分数的计算
			if (roles[0].win() == true) {
				gameEnd(0,playGameCallBack);

				return;
			}
			//机器人出牌 并进行回调
			playGameCallBack.onNext(1);
			ThreeRobotsTurn(playGameCallBack);
			return;
		}

		else {//不合法的出牌 已经在判断合法性的时候返回警告了
			System.out.print("不合法的出牌");
			return;
		}
	}
	private void ThreeRobotsTurn(PlayGameCallBack playGameCallBack) {

		//三个机器人的牌局
		deliveredCardsGroup currentCardsGroup;
		for (int i = 1; i < 4; i++) {

			IsFirstHand(i);
			currentCardsGroup = roles[i].deliver(LatestCards);//这是根据上家的牌获取的机器人应该出的牌

			if (currentCardsGroup.hasCards() == true) {//机器人是有牌出的

				LatestCards = currentCardsGroup;
				roles[i].refreshCardsGroup(currentCardsGroup);//更新牌
				IsLatestShow[i] = true;

				//把更新后的机器人的牌传给presenter
				playGameCallBack.displayRobotCards(currentCardsGroup.getCardsGroup(), i);
				playGameCallBack.setRobotHandCard(roles[i].getHandCards().getCardsGroup(), i);
			} else {
				//机器人不出事件
				IsLatestShow[i] = false;
				playGameCallBack.onRolePass(i);
			}

			//游戏结束了
			if (roles[i].win() == true) {
				gameEnd(i,playGameCallBack);
				return;
			}
			playGameCallBack.onNext((i + 1) % 4);
		}
	}

	private void gameEnd(int winner,PlayGameCallBack playGameCallBack){
		handCardsGroup[] hd = new handCardsGroup[4];
		for (int j = 0; j < 4; j++) {
			hd[j] = roles[j].getHandCards();
		}
		PlayerRepo playerRepo=new PlayerRepo(context);
		Log.e("", "gameEnd: "+ roles[0].getPlayerName());
		int temp = playerRepo.getPlayerByName(roles[0].getPlayerName(), new DbCallBack.RankCallBack() {
			@Override
			public void dispalyRank(String name, int score, int rank,String ary[][]) { }
		}).getScore();

		int GameScore = scorer.getScore(0, hd);//这局游戏的分数
		int PlayerScore =  GameScore + temp;//玩家的新分数

		//更新数据库
		Player player2=new Player(roles[0].getPlayerName());
		player2.setScore(PlayerScore);
		playerRepo.update(player2);


		playGameCallBack.onGameEnd(winner, GameScore);
	}

	public void escapeGame(PlayGameCallBack playGameCallBack) {
		PlayerRepo playerRepo=new PlayerRepo(context);

		int temp = playerRepo.getPlayerByName(roles[0].getPlayerName(), new DbCallBack.RankCallBack() {
			@Override
			public void dispalyRank(String name, int score, int rank,String ary[][]) { }
		}).getScore();
		int PlayerScore =  temp - 200;//玩家扣200分
		//更新数据库
		Player player2=new Player(roles[0].getPlayerName());
		player2.setScore(PlayerScore);
		playerRepo.update(player2);
		//TODO 还要补一个逃跑对话框 告诉玩家逃跑有惩罚
	}

}