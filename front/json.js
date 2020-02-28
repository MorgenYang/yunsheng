const tabList = [{
	name: '会议报道',
	id: '1',
}, {
	name: '会议视频',
	id: '2'
}, {
	name: '在线课堂',
	id: '3'
}];
const newsList = [{
		id: 1,
		title: '春节宅在家，看看这份片单吧',
		content: '推荐影片: 囧妈、饮食男女、老妈操碎心、未来同学会',
		author: '新京报',
		image:'http://t8.baidu.com/it/u=2247852322,986532796&fm=79&app=86&f=JPEG?w=1280&h=853',
		time: '2020.1.26',
		type: 1
	},{
		id: 2,
		title: '在闲鱼上捡漏是怎样一种体验？',
		content: '闲鱼是阿里巴巴旗下的一款产品，笔者也是接触闲鱼很多了，分享一些事情给大家。说实话咸鱼捡漏还是很可能的，买对了肯定比你正规渠道买的便宜很多，当然你得花费一定的精力。我在咸鱼买了很多很多东西，至今还没碰到翻车的情况',
		author: '爱考过',
		image:'https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2133231534,4242817610&fm=173&app=49&f=JPEG?w=218&h=146&s=4FB42BC55E2A26076B2D1301030060C6',
		time: '2020.1.26',
		type: 1
	},{
		id: 3,
		title: '关于微信，你可能不知道的10个实用小技巧',
		content: '不到8年的时间，微信迅速占领了我们的生活，成为了我们学习，生活和工作的一部分。作为微信的老用户，亓纪在使用过程中找到并发现了10个实用且隐蔽的微信小技巧，想通过这篇文章分享给大家，让大家在使用微信的过程中更加方便，更加得心应手',
		author: '新京报',
		image:'http://t8.baidu.com/it/u=2247852322,986532796&fm=79&app=86&f=JPEG?w=1280&h=853',
		time: '2020.1.26',
		type: 1
	},{
		id: 4,
		title: '这10台新车，2020年能买一台，朋友圈绝对有面子',
		content: '捷达VS7,新一代本田飞度',
		author: '爱考过',
		image:'https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2133231534,4242817610&fm=173&app=49&f=JPEG?w=218&h=146&s=4FB42BC55E2A26076B2D1301030060C6',
		time: '2020.1.26',
		type: 1
	},{
		id: 5,
		title: '别再玩手机了，赶紧学前端赶紧学前端',
		content: '防护等级非的翻江倒海父节点劲是多喝水更待何时回到家发货单',
		author: '爱考过',
		image:'https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2133231534,4242817610&fm=173&app=49&f=JPEG?w=218&h=146&s=4FB42BC55E2A26076B2D1301030060C6',
		time: '2020.1.26',
		type: 1
	},{
		id: 6,
		title: '别再玩手机了，赶紧学前端赶紧学前端',
		content: '防护等级非的翻江倒海父节点劲是多喝水更待何时回到家发货单',
		author: '爱考过',
		image:'https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2133231534,4242817610&fm=173&app=49&f=JPEG?w=218&h=146&s=4FB42BC55E2A26076B2D1301030060C6',
		time: '2020.1.26',
		type: 1
	}
];

const videoList = [{
		id: 1,
		title: '将府公园',
		url:"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=129764&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss",
		content: 'fhdfh涣发大号反间谍法孵涣发大号反间谍法孵化基地很费劲地方还返绝代风华涣发大号反间谍法孵化基地很费劲地方还返绝代风华化基地很费劲地方还返绝代风华',
		author: '新京报',
		image:'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=267016837,3577870102&fm=26&gp=0.jpg',
		time: '2020.1.26',
		//initialTime:0,
		type: 1
	} ,{
		id: 2,
		title: '雪景',
		content: '防护等级非的翻江倒海父节点劲是多喝水更待何时回到家发货单',
		author: '爱考过',
		url:"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=164016&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss",
		image:'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1580370484666&di=5aae16052fb42ab48783495f81d33e9c&imgtype=0&src=http%3A%2F%2Fimg.jk51.com%2Fimg_jk51%2F389919186.jpeg',
		time: '2020.1.26',
		//initialTime:0,
		type: 1
	},{
		id: 3,
		title: '运动',
		content: 'fhdfh涣发大号反间谍法孵涣发大号反间谍法孵化基地很费劲地方还返绝代风华涣发大号反间谍法孵化基地很费劲地方还返绝代风华化基地很费劲地方还返绝代风华',
		author: '新京报',
		url:"http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
		image:'http://hbimg.b0.upaiyun.com/357d23d074c2954d568d1a6f86a5be09d190a45116e95-0jh9Pg_fw658',
		time: '2020.1.26',
		//initialTime:0,
		type: 1
	},{
		id: 4,
		title: '猫和老鼠',
		content: '防护等级非的翻江倒海父节点劲是多喝水更待何时回到家发货单',
		author: '爱考过',
		url:"http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
		image:'http://img1.imgtn.bdimg.com/it/u=415411061,2653953890&fm=26&gp=0.jpg',
		time: '2020.1.26',
		//initialTime:0,
		type: 1
	} 
];

const onlineClassList =[
	{
		name:"周医生",
		photo:"周医生",
		time:"5分钟前",
		isAttention:0,
		division:"胸外科",
		title:"减肥不吃晚饭可以瘦吗",
		content:"科学饮食减肥法,通过科学控制日常饮食达到减肥的目的.",
		image:'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=267016837,3577870102&fm=26&gp=0.jpg',
		lookCount:1580,
		msgCount:579,
		attentionCount:1100
	},
	{
		name:"李医生",
		photo:"周医生",
		time:"5分钟前",
		isAttention:1,
		image:'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=267016837,3577870102&fm=26&gp=0.jpg',
		division:"胸外科",
		title:"糖尿病患者该怎么吃",
		content:"科学饮食搭配,帮助糖尿病患者合理安排日常三餐",
		lookCount:1342,
		msgCount:342,
		attentionCount:1623
	},
	{
		name:"周医生",
		photo:"周医生",
		time:"5分钟前",
		isAttention:0,
		image:'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1580370484666&di=5aae16052fb42ab48783495f81d33e9c&imgtype=0&src=http%3A%2F%2Fimg.jk51.com%2Fimg_jk51%2F389919186.jpeg',
		division:"胸外科",
		title:"减肥不吃晚饭可以瘦吗",
		content:"科学饮食减肥法,通过科学控制日常饮食达到减肥的目的",
		lookCount:1580,
		msgCount:579,
		attentionCount:1267
	},
	{
		name:"周医生",
		photo:"周医生",
		time:"5分钟前",
		isAttention:0,
		image:'http://hbimg.b0.upaiyun.com/357d23d074c2954d568d1a6f86a5be09d190a45116e95-0jh9Pg_fw658',
		division:"胸外科",
		title:"减肥不吃晚饭可以瘦吗",
		content:"科学饮食减肥法,通过科学控制日常饮食达到减肥的目的",
		lookCount:1311,
		msgCount:431,
		attentionCount:766
	},{
		name:"周医生",
		photo:"周医生",
		time:"5分钟前",
		isAttention:0,
		image:'http://img1.imgtn.bdimg.com/it/u=415411061,2653953890&fm=26&gp=0.jpg',
		division:"胸外科",
		title:"减肥不吃晚饭可以瘦吗",
		content:"科学饮食减肥法,通过科学控制日常饮食达到减肥的目的",
		lookCount:1382,
		msgCount:129,
		attentionCount:1012
	}
	
	
]

const evaList = [{
		src: 'http://gss0.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/77c6a7efce1b9d1663174705fbdeb48f8d546486.jpg',
		nickname: 'Ranth Allngal',
		time: '09-20 12:54',
		zan: '54',
		content: '评论不要太苛刻，不管什么产品都会有瑕疵，客服也说了可以退货并且商家承担运费，我觉得至少态度就可以给五星。'
	},
	{
		src: 'http://img0.imgtn.bdimg.com/it/u=2396068252,4277062836&fm=26&gp=0.jpg',
		nickname: 'Ranth Allngal',
		time: '09-20 12:54',
		zan: '54',
		content: '楼上说的好有道理。'
	}
]

export default {
	tabList,
	newsList,
	videoList,
	onlineClassList,
	evaList
}
