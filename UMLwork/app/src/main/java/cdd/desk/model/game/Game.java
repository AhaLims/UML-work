package cdd.desk.model.game;

import java.util.List;

import cdd.desk.model.PlayGameCallBack;
import cdd.desk.model.card.Card;
import cdd.desk.model.card.CardColor;
import cdd.desk.model.card.PairCardsGroup;
import cdd.desk.model.card.deliveredCardsGroup;
import cdd.desk.model.card.handCardsGroup;
import cdd.desk.model.role.Player;
import cdd.desk.model.role.Role;



public class Game{
	private Judger judger;
	private PairCardsGroup AllCards;
	private Scorer scorer;
	//CardsManager cardsManager;
	private deliveredCardsGroup LatestCards;//最新的 出在牌桌上面的牌
	private int currentTurn;
	public int firstTurn;//其实不应该public的....后面再改吧
	private int turnTime;//轮数
	private Role[] roles;
	private boolean[] IsLatestShow;//用来判断最新是否出了牌
	//private int[] nextTurn;
	public Game() {
		judger = new Judger();
		scorer = new Scorer();
		//nextTurn = new int[4];
		AllCards = PairCardsGroup.getPairOfCards();
		roles = new Role[4];
		IsLatestShow = new boolean[4];
		for(int i = 0;i < 4; i++) {
			roles[i] = new Player(this);
			IsLatestShow[i] = false;//一开始大家都没有出牌
		}

		//roles[1] = new Robot(this);
		//roles[2] = new Robot(this);
		//roles[3] = new Robot(this);

	}
	private boolean IsFirstHand(int index) {//用来判断是不是先手
		for(int i = 0;i < 4;i++) {
			if (i == index)continue;
			else {
				if(IsLatestShow[i] == true)return false;
			}
		}
		return true;
	}


	//出牌的函数
	//传入当前的index.....
	//以及准备出的牌
	//返回boolean值代表出的牌是不是合法的
	//也许应该返回String 比较合适？ 如果是合法 则String为空     //不合法 String为相应的错误提示
	//TODO 需要修改 因为需要根据是否先手 后手来判断出牌
	public boolean RoleDeliverCard(int currentRole,deliveredCardsGroup currentCardsGroup) {
		boolean validation = false;

		if(firstTurn == currentRole && turnTime == 1)//第一轮的先手
		{
			LatestCards = new deliveredCardsGroup();//先手 LatestCards 应该是新new的
			//必须有方块三
			if(currentCardsGroup.canFindCard(3, CardColor.Diamond) != -1)
			{
				roles[currentRole].refreshCardsGroup(currentCardsGroup);//更新牌
				validation = true;
			}
			IsLatestShow[currentRole] = true;//出了牌
		}
		if(IsFirstHand(currentRole) == true)//非第一轮的先手
		{
			IsLatestShow[currentRole] = true;//出了牌
			LatestCards = new deliveredCardsGroup();//先手 LatestCards 应该是新new的
			if(currentCardsGroup.hasCards() == false)//TODO 这里是说明先手不出牌 是不合法的
			{
				validation = false;
				//应该返回给前端提示 说明这是不合法的行为
			}
			//有选中的牌了
			else {
				if(judger.isPermissible(null,currentCardsGroup) == true)
				{
					roles[currentRole].refreshCardsGroup(currentCardsGroup);//更新牌
					validation = true;
				}
			}
		}
		else {//后手 需要参考上家的牌
			if( judger.isPermissible(LatestCards,currentCardsGroup) == true)
			{	//后手出牌合法
				//更新牌
				roles[currentRole].refreshCardsGroup(currentCardsGroup);//更新牌
				validation = true;
				IsLatestShow[currentRole] = true;//出了牌
			}
		}

		System.out.print("现在游戏进行到第 ");
		System.out.print(turnTime);
		System.out.println("轮");
		System.out.print("当前出牌的Index为");
		System.out.println(currentRole);
		System.out.print("他选择");
		if(validation == true)
		{
			System.out.print("【出牌】，并且为\n");
			currentCardsGroup.showDetail();//展示牌组细节的数组
			LatestCards = currentCardsGroup;//更新牌桌上的最后一组牌
		}

		else
			System.out.println("不出牌");

		return validation;
	}


	public void InitGame(PlayGameCallBack playGameCallBack) {
		AllCards.shuffleCards();//洗牌
		licensingCards();//发牌
		turnTime = 0;
        playGameCallBack.displayPlayerHandCards(roles[0].getHandCards().getCardsGroup());//回调
		for(int i = 1;i < 4; i++){
			playGameCallBack.setRobotHandCard(roles[i].getHandCards().getCardsGroup(),i);//将机器人的牌传给前端
		}
		for(int i = 0;i < 4;i++){
			//在控制台打印所有人的牌
			System.out.print("当前第");
			System.out.print(i);
			System.out.println("个玩家的牌为:");
			roles[i].getHandCards().showDetail();
		}

		if(firstTurn == 0) return;//第一个出牌的是玩家 则初始化工作完成
		else//如果第一个出牌的不是玩家 那么应该先模拟机器人的出牌
		{
			deliveredCardsGroup deliveredCard;
			for(int i = firstTurn;i < 4;i++){//机器人出牌 并进行回调
				deliveredCard = roles[i].deliver(LatestCards);
				if(deliveredCard.hasCards() == true)
				{
					LatestCards = deliveredCard;//更新LatestCards
				}
				playGameCallBack.displayRobotCards(deliveredCard.getCardsGroup(),i);

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
	//TODO 处理前端的　"不出事件"
	public void turn(List<Integer> list,PlayGameCallBack playGameCallBack) {
		boolean validation = false;
		turnTime++;
		deliveredCardsGroup currentCardsGroup = roles[0].selectCards(list);
		if(turnTime == 1)//第一轮游戏
		{
			// TODO 必须有方块三 后面再补充这部分吧...
			//修改validation

		}
		validation = true;//TODO 这里先默认了所有的出牌都是合法的....后面再改
		//玩家的牌传递给presenter
		if(validation == true) {//合法的出牌
			playGameCallBack.displayPlayerCards(currentCardsGroup.getCardsGroup());
			if(roles[0].win() == true){
				//游戏结束了  需要进行分数的计算
				//TODO 需要测试分数
				handCardsGroup [] hd = new handCardsGroup[4];
				for(int i = 0;i<4;i++){
					hd[i] = roles[i].getHandCards();
				}
				int PlayerScore = scorer.getScore(0,hd);//传牌组进去....
				playGameCallBack.onGameWin(0,PlayerScore);
			}
			for(int i = 1; i < 4; i++){
				currentCardsGroup = roles[i].deliver(LatestCards);
				if(currentCardsGroup.hasCards() == true)
					LatestCards = currentCardsGroup;
				playGameCallBack.displayRobotCards(currentCardsGroup.getCardsGroup(),i);
				if(roles[i].win() == true){
					//游戏结束了
					//TODO 需要测试分数
					handCardsGroup [] hd = new handCardsGroup[4];
					for(int j = 0; j < 4; j++){
						hd[j] = roles[j].getHandCards();
					}
					int PlayerScore = scorer.getScore(0,hd);//传牌组进去....
					playGameCallBack.onGameWin(i,PlayerScore);
				}
				//机器人出牌 并进行回调
			}
		}
		else{//不合法的出牌 返回警告
			playGameCallBack.onCardsNotValid("不合法蛤蛤蛤");
		}

	}



}