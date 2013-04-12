package it.cybion.influencers.filtering.topologybased;


import it.cybion.influencers.crawler.filtering.topologybased.InAndOutDegreeFilterManager;
import it.cybion.influencers.crawler.graph.GraphFacade;
import it.cybion.influencers.crawler.graph.Neo4jGraphFacade;
import it.cybion.influencers.crawler.graph.indexes.GraphIndexType;
import it.cybion.influencers.cache.TwitterCache;
import it.cybion.influencers.cache.TwitterFacadeFactory;
import it.cybion.influencers.cache.persistance.PersistanceFacade;
import it.cybion.influencers.cache.persistance.PersistanceFacade;
import it.cybion.influencers.cache.web.Token;
import it.cybion.influencers.cache.web.TwitterWebFacade;
import it.cybion.influencers.crawler.utils.FilesDeleter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import twitter4j.TwitterException;



public class InAndOutDegreeFilterManagerTEST
{

	private static final Logger logger = Logger.getLogger(InAndOutDegreeFilterManagerTEST.class);

	private static TwitterCache twitterFacade;
	private static GraphFacade graphFacade;

	public static void init() throws IOException
	{
		twitterFacade = TwitterFacadeFactory.getTwitterFacadeForTests();
	}

	public static void main(String args[]) throws IOException, TwitterException
	{
		init();

		List<Long> usersIds = new ArrayList<Long>();

		/*
		 * This is the result of first iteration (inDegree=10% + outDegree=5% +
		 * descriptionDictionaryFilter)
		 */

		usersIds.add(359231506L);
		usersIds.add(205715015L);
		usersIds.add(550171914L);
		usersIds.add(577297221L);
		usersIds.add(52110205L);
		usersIds.add(140776259L);
		usersIds.add(541768154L);
		usersIds.add(470237508L);
		usersIds.add(330227615L);
		usersIds.add(410109893L);
		usersIds.add(138029340L);
		usersIds.add(198818462L);
		usersIds.add(54321798L);
		usersIds.add(195164635L);
		usersIds.add(36359277L);
		usersIds.add(376991861L);
		usersIds.add(12993022L);
		usersIds.add(108411822L);
		usersIds.add(518392799L);
		usersIds.add(56814502L);
		usersIds.add(394010458L);
		usersIds.add(702720528L);
		usersIds.add(127632449L);
		usersIds.add(385910975L);
		usersIds.add(204358279L);
		usersIds.add(227272869L);
		usersIds.add(106455042L);
		usersIds.add(292319000L);
		usersIds.add(573987754L);
		usersIds.add(702835705L);
		usersIds.add(388129232L);
		usersIds.add(34717311L);
		usersIds.add(93065477L);
		usersIds.add(18749668L);
		usersIds.add(70731786L);
		usersIds.add(246361193L);
		usersIds.add(105169554L);
		usersIds.add(226092023L);
		usersIds.add(300510501L);
		usersIds.add(201207305L);
		usersIds.add(94063500L);
		usersIds.add(71026979L);
		usersIds.add(281987950L);
		usersIds.add(35003378L);
		usersIds.add(37650384L);
		usersIds.add(107554109L);
		usersIds.add(329712103L);
		usersIds.add(117701249L);
		usersIds.add(573376838L);
		usersIds.add(385888951L);
		usersIds.add(17652645L);
		usersIds.add(321988106L);
		usersIds.add(36142154L);
		usersIds.add(468316108L);
		usersIds.add(167322351L);
		usersIds.add(197393212L);
		usersIds.add(189908238L);
		usersIds.add(359955419L);
		usersIds.add(24028644L);
		usersIds.add(536701698L);
		usersIds.add(300164030L);
		usersIds.add(15164194L);
		usersIds.add(283033025L);
		usersIds.add(116035984L);
		usersIds.add(119316571L);
		usersIds.add(338686158L);
		usersIds.add(603891853L);
		usersIds.add(475648031L);
		usersIds.add(250195430L);
		usersIds.add(499131541L);
		usersIds.add(130077416L);
		usersIds.add(37908680L);
		usersIds.add(614308979L);
		usersIds.add(74458253L);
		usersIds.add(247348938L);
		usersIds.add(276877879L);
		usersIds.add(9407212L);
		usersIds.add(400659732L);
		usersIds.add(92294198L);
		usersIds.add(21885975L);
		usersIds.add(253956088L);
		usersIds.add(97082282L);
		usersIds.add(80397413L);
		usersIds.add(373342103L);
		usersIds.add(312557231L);
		usersIds.add(202187051L);
		usersIds.add(182095745L);
		usersIds.add(414704682L);
		usersIds.add(106195223L);
		usersIds.add(15423900L);
		usersIds.add(291710084L);
		usersIds.add(93845688L);
		usersIds.add(447867639L);
		usersIds.add(308923423L);
		usersIds.add(540873L);
		usersIds.add(116779150L);
		usersIds.add(430745136L);
		usersIds.add(94526140L);
		usersIds.add(97694361L);
		usersIds.add(28131595L);
		usersIds.add(249417811L);
		usersIds.add(24063959L);
		usersIds.add(221772714L);
		usersIds.add(123603520L);
		usersIds.add(318003165L);
		usersIds.add(104944098L);
		usersIds.add(10076702L);
		usersIds.add(71564326L);
		usersIds.add(400120423L);
		usersIds.add(262170287L);
		usersIds.add(262560084L);
		usersIds.add(136396440L);
		usersIds.add(810429380L);
		usersIds.add(114454012L);
		usersIds.add(20533665L);
		usersIds.add(438328159L);
		usersIds.add(382034286L);
		usersIds.add(990840270L);
		usersIds.add(184855961L);
		usersIds.add(386248749L);
		usersIds.add(376410500L);
		usersIds.add(522930447L);
		usersIds.add(398528037L);
		usersIds.add(62372065L);
		usersIds.add(6876542L);
		usersIds.add(449125266L);
		usersIds.add(64511804L);
		usersIds.add(14146523L);
		usersIds.add(125395152L);
		usersIds.add(64362457L);
		usersIds.add(110476224L);
		usersIds.add(17269271L);
		usersIds.add(39734148L);
		usersIds.add(62527874L);
		usersIds.add(266019694L);
		usersIds.add(142305918L);
		usersIds.add(201389969L);
		usersIds.add(558770495L);
		usersIds.add(625670417L);
		usersIds.add(338950381L);
		usersIds.add(37377114L);
		usersIds.add(474891842L);
		usersIds.add(191075997L);
		usersIds.add(436969803L);
		usersIds.add(60836278L);
		usersIds.add(80661985L);
		usersIds.add(787169790L);
		usersIds.add(507058183L);
		usersIds.add(411407388L);
		usersIds.add(392605890L);
		usersIds.add(397285294L);
		usersIds.add(22008618L);
		usersIds.add(572728909L);
		usersIds.add(543283828L);
		usersIds.add(99748543L);
		usersIds.add(15714619L);
		usersIds.add(452880768L);
		usersIds.add(299618775L);
		usersIds.add(104504815L);
		usersIds.add(521599512L);
		usersIds.add(442373150L);
		usersIds.add(414855894L);
		usersIds.add(265490629L);
		usersIds.add(199696283L);
		usersIds.add(12967642L);
		usersIds.add(106133073L);
		usersIds.add(420188464L);
		usersIds.add(484595055L);
		usersIds.add(347780800L);
		usersIds.add(138255491L);
		usersIds.add(204227718L);
		usersIds.add(703195747L);
		usersIds.add(203438938L);
		usersIds.add(53969121L);
		usersIds.add(473257273L);
		usersIds.add(22133576L);
		usersIds.add(320743576L);
		usersIds.add(55658279L);
		usersIds.add(241520995L);
		usersIds.add(451296820L);
		usersIds.add(211938604L);
		usersIds.add(27344369L);
		usersIds.add(380822041L);
		usersIds.add(486840412L);
		usersIds.add(16113204L);
		usersIds.add(395980003L);
		usersIds.add(394717427L);
		usersIds.add(360677803L);
		usersIds.add(433019693L);
		usersIds.add(126301664L);
		usersIds.add(53651501L);
		usersIds.add(311476973L);
		usersIds.add(150692280L);
		usersIds.add(821146950L);
		usersIds.add(94614754L);
		usersIds.add(317912030L);
		usersIds.add(379930125L);
		usersIds.add(359328898L);
		usersIds.add(89542425L);
		usersIds.add(61584018L);
		usersIds.add(251070898L);
		usersIds.add(323824570L);
		usersIds.add(336744649L);
		usersIds.add(158082019L);
		usersIds.add(307994096L);
		usersIds.add(479819134L);
		usersIds.add(547661953L);
		usersIds.add(403436826L);
		usersIds.add(720300031L);
		usersIds.add(75802704L);
		usersIds.add(100276383L);
		usersIds.add(582102544L);
		usersIds.add(20162702L);
		usersIds.add(245844113L);
		usersIds.add(267680185L);
		usersIds.add(420439204L);
		usersIds.add(270918639L);
		usersIds.add(160906189L);
		usersIds.add(13672912L);
		usersIds.add(96389345L);
		usersIds.add(75234431L);
		usersIds.add(93971980L);
		usersIds.add(38634143L);
		usersIds.add(189506658L);
		usersIds.add(569495824L);
		usersIds.add(448923449L);
		usersIds.add(505936203L);
		usersIds.add(80530081L);
		usersIds.add(184821730L);
		usersIds.add(365067870L);
		usersIds.add(108954456L);
		usersIds.add(454986448L);
		usersIds.add(596343621L);
		usersIds.add(96614832L);
		usersIds.add(187454181L);
		usersIds.add(104780504L);
		usersIds.add(238184792L);
		usersIds.add(121237035L);
		usersIds.add(334730999L);
		usersIds.add(121709820L);
		usersIds.add(411810147L);
		usersIds.add(439177907L);
		usersIds.add(69006473L);
		usersIds.add(548208979L);
		usersIds.add(937114999L);
		usersIds.add(89418162L);
		usersIds.add(151414100L);
		usersIds.add(240283493L);
		usersIds.add(352968483L);
		usersIds.add(365159571L);
		usersIds.add(16907137L);
		usersIds.add(322546836L);
		usersIds.add(202131344L);
		usersIds.add(113097719L);
		usersIds.add(48652501L);
		usersIds.add(245714279L);
		usersIds.add(479650495L);
		usersIds.add(112225767L);
		usersIds.add(53885203L);
		usersIds.add(139444047L);
		usersIds.add(219340924L);
		usersIds.add(247890026L);
		usersIds.add(319646224L);
		usersIds.add(59729262L);
		usersIds.add(267404161L);
		usersIds.add(256465678L);
		usersIds.add(216479996L);
		usersIds.add(41555508L);
		usersIds.add(71803722L);
		usersIds.add(219679642L);
		usersIds.add(302195691L);
		usersIds.add(14605307L);
		usersIds.add(216346035L);
		usersIds.add(37283934L);
		usersIds.add(208953931L);
		usersIds.add(272634064L);
		usersIds.add(95065399L);
		usersIds.add(55201968L);
		usersIds.add(31370548L);
		usersIds.add(226904580L);
		usersIds.add(50985244L);
		usersIds.add(396845110L);
		usersIds.add(56397924L);
		usersIds.add(523642610L);
		usersIds.add(224736736L);
		usersIds.add(418614287L);
		usersIds.add(423536940L);
		usersIds.add(534667257L);
		usersIds.add(983370686L);
		usersIds.add(7856222L);
		usersIds.add(628524838L);
		usersIds.add(158861650L);
		usersIds.add(47188403L);
		usersIds.add(325459258L);
		usersIds.add(203024150L);
		usersIds.add(81877841L);
		usersIds.add(15882380L);
		usersIds.add(413837617L);
		usersIds.add(275908182L);
		usersIds.add(52763812L);
		usersIds.add(381136273L);
		usersIds.add(104169660L);
		usersIds.add(45876993L);
		usersIds.add(142701193L);
		usersIds.add(85925308L);
		usersIds.add(435814031L);
		usersIds.add(466554214L);
		usersIds.add(249224312L);
		usersIds.add(443051637L);
		usersIds.add(240756129L);
		usersIds.add(130115893L);
		usersIds.add(464217456L);
		usersIds.add(123179677L);
		usersIds.add(175427476L);
		usersIds.add(6612922L);
		usersIds.add(188338664L);
		usersIds.add(36155976L);
		usersIds.add(84030659L);
		usersIds.add(238169246L);
		usersIds.add(52360721L);
		usersIds.add(133632304L);
		usersIds.add(440323186L);
		usersIds.add(82320727L);
		usersIds.add(252280240L);
		usersIds.add(160910618L);
		usersIds.add(983322048L);
		usersIds.add(79410677L);
		usersIds.add(376728296L);
		usersIds.add(74420805L);
		usersIds.add(22005661L);
		usersIds.add(221789578L);
		usersIds.add(57573169L);
		usersIds.add(77585934L);
		usersIds.add(167874623L);
		usersIds.add(24176877L);
		usersIds.add(296871421L);
		usersIds.add(6825222L);
		usersIds.add(424334921L);
		usersIds.add(165180394L);
		usersIds.add(419016106L);
		usersIds.add(10130572L);
		usersIds.add(461152120L);
		usersIds.add(109071318L);
		usersIds.add(237692888L);
		usersIds.add(255443907L);
		usersIds.add(135069327L);
		usersIds.add(863378755L);
		usersIds.add(174944629L);
		usersIds.add(193618414L);
		usersIds.add(47886512L);
		usersIds.add(33994140L);
		usersIds.add(254673252L);
		usersIds.add(430562931L);
		usersIds.add(153835703L);
		usersIds.add(92089113L);
		usersIds.add(373247380L);
		usersIds.add(406457442L);
		usersIds.add(26682734L);
		usersIds.add(15767066L);
		usersIds.add(90834644L);
		usersIds.add(904340401L);
		usersIds.add(535818819L);
		usersIds.add(358795296L);
		usersIds.add(14940110L);
		usersIds.add(16953120L);
		usersIds.add(339933268L);
		usersIds.add(412953255L);
		usersIds.add(145764563L);
		usersIds.add(209575188L);
		usersIds.add(321365664L);
		usersIds.add(403355031L);
		usersIds.add(469377465L);
		usersIds.add(136589813L);
		usersIds.add(372246422L);
		usersIds.add(29215862L);
		usersIds.add(460972705L);
		usersIds.add(372518181L);
		usersIds.add(60910727L);
		usersIds.add(557629455L);
		usersIds.add(283547722L);
		usersIds.add(298218572L);
		usersIds.add(352905283L);
		usersIds.add(729140696L);
		usersIds.add(88780795L);
		usersIds.add(46435908L);
		usersIds.add(511695046L);
		usersIds.add(490558029L);
		usersIds.add(439286029L);
		usersIds.add(88864861L);
		usersIds.add(29571345L);
		usersIds.add(187577912L);
		usersIds.add(273424243L);
		usersIds.add(871434764L);
		usersIds.add(271018040L);
		usersIds.add(186380929L);
		usersIds.add(246206657L);
		usersIds.add(144859362L);
		usersIds.add(71006175L);
		usersIds.add(216029027L);
		usersIds.add(350898321L);
		usersIds.add(617168434L);
		usersIds.add(554580254L);
		usersIds.add(209130541L);
		usersIds.add(273427458L);
		usersIds.add(936493549L);
		usersIds.add(217792901L);
		usersIds.add(469460774L);
		usersIds.add(29176328L);
		usersIds.add(121856537L);
		usersIds.add(224576141L);
		usersIds.add(105226949L);
		usersIds.add(455651350L);
		usersIds.add(21640087L);
		usersIds.add(308123568L);
		usersIds.add(370119613L);
		usersIds.add(133400538L);
		usersIds.add(104133606L);
		usersIds.add(16524753L);
		usersIds.add(636367480L);
		usersIds.add(296329647L);
		usersIds.add(406920669L);
		usersIds.add(347472088L);
		usersIds.add(73342581L);
		usersIds.add(15380717L);
		usersIds.add(140061702L);
		usersIds.add(439596849L);
		usersIds.add(99981345L);
		usersIds.add(445629424L);
		usersIds.add(38409012L);
		usersIds.add(334588799L);
		usersIds.add(173521597L);
		usersIds.add(156643951L);
		usersIds.add(23943168L);
		usersIds.add(14443340L);
		usersIds.add(276254428L);
		usersIds.add(83072478L);
		usersIds.add(496043012L);
		usersIds.add(169005797L);
		usersIds.add(7068702L);
		usersIds.add(1011114536L);
		usersIds.add(471877852L);
		usersIds.add(155242729L);
		usersIds.add(160229328L);
		usersIds.add(52811632L);
		usersIds.add(60997832L);
		usersIds.add(64738375L);
		usersIds.add(449034347L);
		usersIds.add(154089471L);
		usersIds.add(280706357L);
		usersIds.add(581950689L);
		usersIds.add(501085807L);
		usersIds.add(870052778L);
		usersIds.add(148349190L);
		usersIds.add(1026737030L);
		usersIds.add(40891450L);
		usersIds.add(299655583L);
		usersIds.add(259858825L);
		usersIds.add(96968685L);
		usersIds.add(163779339L);
		usersIds.add(465632019L);
		usersIds.add(79145632L);
		usersIds.add(299020796L);
		usersIds.add(282465252L);
		usersIds.add(488284620L);
		usersIds.add(82632437L);
		usersIds.add(31118563L);
		usersIds.add(390126821L);
		usersIds.add(472798515L);
		usersIds.add(394749104L);
		usersIds.add(20638158L);
		usersIds.add(61462415L);
		usersIds.add(64669665L);
		usersIds.add(372220451L);
		usersIds.add(254694670L);
		usersIds.add(212610457L);
		usersIds.add(335825808L);
		usersIds.add(722866314L);
		usersIds.add(38297672L);
		usersIds.add(549569816L);
		usersIds.add(124225234L);
		usersIds.add(111245660L);
		usersIds.add(488864885L);
		usersIds.add(731040823L);
		usersIds.add(44469572L);
		usersIds.add(419607944L);
		usersIds.add(430852554L);
		usersIds.add(30710905L);
		usersIds.add(114798254L);
		usersIds.add(92071380L);
		usersIds.add(302735634L);
		usersIds.add(386207264L);
		usersIds.add(883898040L);
		usersIds.add(269893073L);
		usersIds.add(19849249L);
		usersIds.add(484927634L);
		usersIds.add(15862645L);
		usersIds.add(14337211L);
		usersIds.add(251195258L);
		usersIds.add(9995222L);
		usersIds.add(341747372L);
		usersIds.add(14761145L);
		usersIds.add(131854825L);
		usersIds.add(402412965L);
		usersIds.add(131559483L);
		usersIds.add(428265479L);
		usersIds.add(61301425L);
		usersIds.add(612454349L);
		usersIds.add(54487870L);
		usersIds.add(815714022L);
		usersIds.add(423410028L);
		usersIds.add(240671826L);
		usersIds.add(182741202L);
		usersIds.add(288961294L);
		usersIds.add(131924836L);
		usersIds.add(472833806L);
		usersIds.add(302821495L);
		usersIds.add(86268188L);
		usersIds.add(257075188L);
		usersIds.add(732596430L);
		usersIds.add(127050479L);
		usersIds.add(124186827L);
		usersIds.add(7151312L);
		usersIds.add(268254048L);
		usersIds.add(362398662L);
		usersIds.add(354558264L);
		usersIds.add(38631437L);
		usersIds.add(396495123L);
		usersIds.add(91641939L);
		usersIds.add(231122009L);
		usersIds.add(109532697L);
		usersIds.add(146570863L);
		usersIds.add(528752504L);
		usersIds.add(387974558L);
		usersIds.add(404893024L);
		usersIds.add(35188646L);
		usersIds.add(92111360L);
		usersIds.add(348370024L);
		usersIds.add(621724115L);
		usersIds.add(379722184L);
		usersIds.add(308073259L);
		usersIds.add(305556240L);
		usersIds.add(546974382L);
		usersIds.add(50340457L);
		usersIds.add(323335959L);
		usersIds.add(365943696L);
		usersIds.add(277910003L);
		usersIds.add(153075438L);
		usersIds.add(96736080L);
		usersIds.add(283014806L);
		usersIds.add(24860170L);
		usersIds.add(114728138L);
		usersIds.add(376151691L);
		usersIds.add(80538870L);
		usersIds.add(304477343L);
		usersIds.add(254348632L);
		usersIds.add(248600078L);
		usersIds.add(1127251L);
		usersIds.add(113094519L);
		usersIds.add(201794143L);
		usersIds.add(113309670L);
		usersIds.add(449968737L);
		usersIds.add(50244027L);
		usersIds.add(422881302L);
		usersIds.add(24763342L);
		usersIds.add(56702762L);
		usersIds.add(428075351L);
		usersIds.add(289062385L);
		usersIds.add(14060276L);
		usersIds.add(37225216L);
		usersIds.add(274014013L);
		usersIds.add(162026900L);
		usersIds.add(22635243L);
		usersIds.add(139390144L);
		usersIds.add(289796133L);
		usersIds.add(489601463L);
		usersIds.add(76627510L);
		usersIds.add(397977207L);
		usersIds.add(172357987L);
		usersIds.add(121801957L);
		usersIds.add(235340710L);
		usersIds.add(153402060L);
		usersIds.add(55653881L);
		usersIds.add(566594290L);
		usersIds.add(24626969L);
		usersIds.add(56693905L);
		usersIds.add(307766385L);
		usersIds.add(34633250L);
		usersIds.add(58566431L);
		usersIds.add(730710516L);
		usersIds.add(36910791L);
		usersIds.add(243202158L);
		usersIds.add(357013045L);
		usersIds.add(152605574L);
		usersIds.add(184139451L);
		usersIds.add(297378499L);
		usersIds.add(478765101L);
		usersIds.add(15446162L);
		usersIds.add(293420717L);
		usersIds.add(292933943L);
		usersIds.add(416459268L);
		usersIds.add(198492254L);
		usersIds.add(50986343L);
		usersIds.add(216247431L);
		usersIds.add(398687163L);
		usersIds.add(96619448L);
		usersIds.add(478532228L);
		usersIds.add(135432018L);
		usersIds.add(522173097L);
		usersIds.add(175505994L);
		usersIds.add(713205810L);
		usersIds.add(413000921L);
		usersIds.add(197558551L);
		usersIds.add(522142798L);
		usersIds.add(35804051L);
		usersIds.add(292390307L);
		usersIds.add(510095791L);
		usersIds.add(231285280L);
		usersIds.add(241656756L);
		usersIds.add(73377282L);
		usersIds.add(93015508L);
		usersIds.add(83394787L);
		usersIds.add(455262218L);
		usersIds.add(92798800L);
		usersIds.add(18560967L);
		usersIds.add(415346073L);
		usersIds.add(344060386L);
		usersIds.add(594070680L);
		usersIds.add(180469200L);
		usersIds.add(15511490L);
		usersIds.add(16357245L);
		usersIds.add(444853097L);
		usersIds.add(457012682L);
		usersIds.add(144171996L);
		usersIds.add(400312105L);
		usersIds.add(273443804L);
		usersIds.add(396785656L);
		usersIds.add(281065445L);
		usersIds.add(96853295L);
		usersIds.add(47565509L);
		usersIds.add(269717465L);
		usersIds.add(221097220L);
		usersIds.add(42842323L);
		usersIds.add(142628561L);
		usersIds.add(295122172L);
		usersIds.add(385870319L);
		usersIds.add(627310473L);
		usersIds.add(210427701L);
		usersIds.add(325378957L);
		usersIds.add(48632826L);
		usersIds.add(221131769L);
		usersIds.add(71261774L);
		usersIds.add(112403678L);
		usersIds.add(222759682L);
		usersIds.add(604612272L);
		usersIds.add(138405382L);
		usersIds.add(73404332L);
		usersIds.add(271398203L);
		usersIds.add(337709748L);
		usersIds.add(365545538L);
		usersIds.add(246959550L);
		usersIds.add(44297230L);
		usersIds.add(119156069L);
		usersIds.add(120380619L);
		usersIds.add(445473207L);
		usersIds.add(505734159L);
		usersIds.add(138357622L);
		usersIds.add(567920774L);
		usersIds.add(258774289L);
		usersIds.add(92726058L);
		usersIds.add(158067396L);
		usersIds.add(406865793L);
		usersIds.add(331666825L);
		usersIds.add(422577508L);
		usersIds.add(24562353L);
		usersIds.add(245403656L);
		usersIds.add(15529274L);
		usersIds.add(896178158L);
		usersIds.add(14376973L);
		usersIds.add(168501948L);
		usersIds.add(260678396L);
		usersIds.add(132681126L);
		usersIds.add(464959844L);
		usersIds.add(504213777L);
		usersIds.add(28352472L);
		usersIds.add(272667342L);
		usersIds.add(380816026L);
		usersIds.add(231055267L);
		usersIds.add(175085220L);
		usersIds.add(35480285L);
		usersIds.add(473810724L);
		usersIds.add(85786442L);
		usersIds.add(37406027L);
		usersIds.add(399487418L);
		usersIds.add(27200396L);
		usersIds.add(18977805L);
		usersIds.add(193617622L);
		usersIds.add(130233164L);
		usersIds.add(317761572L);
		usersIds.add(109724492L);
		usersIds.add(164716368L);
		usersIds.add(329629782L);
		usersIds.add(203870975L);
		usersIds.add(37219670L);
		usersIds.add(23743152L);
		usersIds.add(228379958L);
		usersIds.add(470109450L);
		usersIds.add(142687721L);
		usersIds.add(313385905L);
		usersIds.add(198781476L);
		usersIds.add(63998579L);
		usersIds.add(454772659L);
		usersIds.add(130743539L);
		usersIds.add(311987336L);
		usersIds.add(105929685L);
		usersIds.add(373311691L);
		usersIds.add(968066173L);
		usersIds.add(45515152L);
		usersIds.add(325174492L);
		usersIds.add(471207656L);
		usersIds.add(399504686L);
		usersIds.add(215050728L);
		usersIds.add(52874601L);
		usersIds.add(810634080L);
		usersIds.add(13208312L);
		usersIds.add(173439144L);
		usersIds.add(208237408L);
		usersIds.add(107008381L);
		usersIds.add(21210455L);
		usersIds.add(128056323L);
		usersIds.add(194048166L);
		usersIds.add(229092881L);
		usersIds.add(231466126L);
		usersIds.add(48520379L);
		usersIds.add(96422323L);
		usersIds.add(359995265L);
		usersIds.add(389967956L);
		usersIds.add(252005619L);
		usersIds.add(269704636L);
		usersIds.add(15018677L);
		usersIds.add(71757602L);
		usersIds.add(21700347L);
		usersIds.add(215031724L);
		usersIds.add(277963412L);
		usersIds.add(83879560L);
		usersIds.add(57390415L);
		usersIds.add(563952509L);
		usersIds.add(426814415L);
		usersIds.add(119115975L);
		usersIds.add(170295397L);
		usersIds.add(567225115L);
		usersIds.add(53367998L);
		usersIds.add(854508618L);
		usersIds.add(443881637L);
		usersIds.add(600117215L);
		usersIds.add(632977290L);
		usersIds.add(245490884L);
		usersIds.add(91536546L);
		usersIds.add(151425063L);
		usersIds.add(118459500L);
		usersIds.add(79311920L);
		usersIds.add(271879547L);
		usersIds.add(468283176L);
		usersIds.add(17421214L);
		usersIds.add(57052200L);
		usersIds.add(454203624L);
		usersIds.add(365510797L);
		usersIds.add(155836036L);
		usersIds.add(402957695L);
		usersIds.add(243381063L);
		usersIds.add(50358694L);
		usersIds.add(115709680L);
		usersIds.add(338560502L);
		usersIds.add(190650448L);
		usersIds.add(241571886L);
		usersIds.add(428502312L);
		usersIds.add(133194261L);
		usersIds.add(400826236L);
		usersIds.add(234449889L);
		usersIds.add(219332419L);
		usersIds.add(6468852L);
		usersIds.add(10277222L);
		usersIds.add(633657340L);
		usersIds.add(15426242L);
		usersIds.add(77193203L);
		usersIds.add(17032442L);
		usersIds.add(408196064L);
		usersIds.add(339659125L);
		usersIds.add(246748090L);
		usersIds.add(68381149L);
		usersIds.add(518433044L);
		usersIds.add(273346893L);
		usersIds.add(329596768L);
		usersIds.add(224967785L);
		usersIds.add(589108196L);
		usersIds.add(241867011L);
		usersIds.add(389370972L);
		usersIds.add(385457259L);
		usersIds.add(90017743L);
		usersIds.add(237246628L);
		usersIds.add(87247035L);
		usersIds.add(241672227L);
		usersIds.add(81575960L);
		usersIds.add(271130152L);
		usersIds.add(298984842L);
		usersIds.add(91138297L);
		usersIds.add(409534995L);
		usersIds.add(480510088L);
		usersIds.add(394308564L);
		usersIds.add(469349084L);
		usersIds.add(257011418L);
		usersIds.add(327429648L);
		usersIds.add(593819910L);
		usersIds.add(369571036L);
		usersIds.add(203630431L);
		usersIds.add(238022966L);
		usersIds.add(89221342L);
		usersIds.add(18285175L);
		usersIds.add(843579242L);
		usersIds.add(493569863L);
		usersIds.add(55060826L);
		usersIds.add(217713333L);
		usersIds.add(111271506L);
		usersIds.add(316277824L);
		usersIds.add(353186683L);
		usersIds.add(80260902L);
		usersIds.add(450950333L);
		usersIds.add(184443418L);
		usersIds.add(112840433L);
		usersIds.add(133236245L);
		usersIds.add(75537200L);
		usersIds.add(338331588L);
		usersIds.add(25791770L);
		usersIds.add(35954473L);
		usersIds.add(46745401L);
		usersIds.add(252010222L);
		usersIds.add(224362274L);
		usersIds.add(41358829L);
		usersIds.add(304315828L);
		usersIds.add(64712764L);
		usersIds.add(144505182L);
		usersIds.add(56785293L);
		usersIds.add(78539528L);
		usersIds.add(28802088L);
		usersIds.add(594856098L);
		usersIds.add(13688402L);
		usersIds.add(249099130L);
		usersIds.add(164237295L);
		usersIds.add(52350001L);
		usersIds.add(345844102L);
		usersIds.add(65944885L);
		usersIds.add(100955846L);
		usersIds.add(92516186L);
		usersIds.add(25979024L);
		usersIds.add(473859930L);
		usersIds.add(87985236L);
		usersIds.add(340196933L);
		usersIds.add(164279618L);
		usersIds.add(17447366L);
		usersIds.add(420407113L);
		usersIds.add(34920636L);
		usersIds.add(166679171L);
		usersIds.add(5605632L);
		usersIds.add(728284958L);
		usersIds.add(284147978L);
		usersIds.add(135627780L);
		usersIds.add(591963435L);
		usersIds.add(69705683L);
		usersIds.add(22624955L);
		usersIds.add(15425803L);
		usersIds.add(481295299L);
		usersIds.add(360710649L);
		usersIds.add(105179723L);
		usersIds.add(286156775L);
		usersIds.add(114499716L);
		usersIds.add(22051890L);
		usersIds.add(19646266L);
		usersIds.add(20299166L);
		usersIds.add(266743945L);
		usersIds.add(336742790L);
		usersIds.add(170373779L);
		usersIds.add(14001032L);
		usersIds.add(71774939L);
		usersIds.add(306399336L);
		usersIds.add(354234669L);
		usersIds.add(26354101L);
		usersIds.add(16245040L);
		usersIds.add(83787698L);
		usersIds.add(507227467L);
		usersIds.add(497127587L);
		usersIds.add(26782958L);
		usersIds.add(244188244L);
		usersIds.add(185190974L);
		usersIds.add(46117429L);
		usersIds.add(330579823L);
		usersIds.add(125486821L);
		usersIds.add(33566610L);
		usersIds.add(415788977L);
		usersIds.add(174200737L);
		usersIds.add(108443669L);
		usersIds.add(92235199L);
		usersIds.add(9347172L);
		usersIds.add(367141179L);
		usersIds.add(334656371L);
		usersIds.add(325519114L);
		usersIds.add(110131093L);
		usersIds.add(53412013L);
		usersIds.add(348152540L);
		usersIds.add(29680897L);
		usersIds.add(244064806L);
		usersIds.add(505864869L);
		usersIds.add(291601991L);
		usersIds.add(234368680L);
		usersIds.add(281515761L);
		usersIds.add(381835137L);
		usersIds.add(181960358L);
		usersIds.add(25520799L);
		usersIds.add(241902218L);
		usersIds.add(414570565L);
		usersIds.add(62500641L);
		usersIds.add(501929729L);
		usersIds.add(27621454L);
		usersIds.add(524511008L);
		usersIds.add(71850983L);
		usersIds.add(492113524L);
		usersIds.add(82593795L);
		usersIds.add(193220955L);
		usersIds.add(250284309L);
		usersIds.add(58411120L);
		usersIds.add(463846774L);
		usersIds.add(387723456L);
		usersIds.add(205716991L);
		usersIds.add(543774984L);
		usersIds.add(18835073L);
		usersIds.add(17347916L);
		usersIds.add(215031217L);
		usersIds.add(429035754L);
		usersIds.add(134131744L);
		usersIds.add(146593792L);
		usersIds.add(69189488L);
		usersIds.add(8102942L);
		usersIds.add(533055536L);
		usersIds.add(145219498L);
		usersIds.add(384186966L);
		usersIds.add(303744923L);
		usersIds.add(41328400L);
		usersIds.add(14282445L);
		usersIds.add(9919532L);
		usersIds.add(414352009L);
		usersIds.add(246183028L);
		usersIds.add(62975550L);
		usersIds.add(273932585L);
		usersIds.add(35425518L);
		usersIds.add(211453853L);
		usersIds.add(545951041L);
		usersIds.add(490798068L);
		usersIds.add(73585910L);
		usersIds.add(868551L);
		usersIds.add(605430641L);
		usersIds.add(16959085L);
		usersIds.add(338924586L);
		usersIds.add(172040568L);
		usersIds.add(475686787L);
		usersIds.add(139643718L);
		usersIds.add(30184354L);
		usersIds.add(156242571L);
		usersIds.add(122115584L);
		usersIds.add(255662125L);
		usersIds.add(833009336L);
		usersIds.add(144527623L);
		usersIds.add(104444731L);
		usersIds.add(198791049L);
		usersIds.add(75642164L);
		usersIds.add(497035616L);
		usersIds.add(589843940L);
		usersIds.add(8641682L);
		usersIds.add(425774841L);
		usersIds.add(60562876L);
		usersIds.add(17786680L);
		usersIds.add(415743L);
		usersIds.add(501162078L);
		usersIds.add(407762156L);
		usersIds.add(625446484L);
		usersIds.add(532357004L);
		usersIds.add(242364755L);
		usersIds.add(104197803L);
		usersIds.add(508091069L);
		usersIds.add(333535020L);
		usersIds.add(42592638L);
		usersIds.add(493075369L);
		usersIds.add(717849745L);
		usersIds.add(502254699L);
		usersIds.add(429756228L);
		usersIds.add(532202923L);
		usersIds.add(174235448L);
		usersIds.add(632947412L);
		usersIds.add(238144871L);
		usersIds.add(197759677L);
		usersIds.add(283916542L);
		usersIds.add(412431451L);
		usersIds.add(90388588L);
		usersIds.add(143475048L);
		usersIds.add(51115363L);
		usersIds.add(287857598L);
		usersIds.add(99914766L);
		usersIds.add(401609741L);
		usersIds.add(603736139L);
		usersIds.add(46705624L);
		usersIds.add(419544330L);
		usersIds.add(121998958L);
		usersIds.add(290549107L);
		usersIds.add(143113151L);
		usersIds.add(41109810L);
		usersIds.add(731021730L);
		usersIds.add(361946875L);
		usersIds.add(542342134L);
		usersIds.add(373227927L);
		usersIds.add(420241364L);
		usersIds.add(410709909L);
		usersIds.add(227304229L);
		usersIds.add(198265065L);
		usersIds.add(137351171L);
		usersIds.add(89967869L);
		usersIds.add(28073922L);
		usersIds.add(31130552L);
		usersIds.add(296708582L);
		usersIds.add(210515935L);
		usersIds.add(16114588L);
		usersIds.add(87919633L);
		usersIds.add(56378335L);
		usersIds.add(360835775L);
		usersIds.add(101325374L);
		usersIds.add(536475461L);
		usersIds.add(742558478L);
		usersIds.add(92091062L);
		usersIds.add(889133287L);
		usersIds.add(40443639L);
		usersIds.add(508745926L);
		usersIds.add(219127927L);
		usersIds.add(195834454L);
		usersIds.add(203977123L);
		usersIds.add(388198151L);
		usersIds.add(147704618L);
		usersIds.add(278074434L);
		usersIds.add(821461L);
		usersIds.add(227271763L);
		usersIds.add(589000006L);
		usersIds.add(384799823L);
		usersIds.add(114148599L);
		usersIds.add(184032481L);
		usersIds.add(234371431L);
		usersIds.add(96400797L);
		usersIds.add(129515952L);
		usersIds.add(557768135L);
		usersIds.add(294719983L);
		usersIds.add(126891101L);
		usersIds.add(222051676L);
		usersIds.add(57601928L);
		usersIds.add(494349723L);
		usersIds.add(360495808L);
		usersIds.add(118727314L);
		usersIds.add(19342858L);
		usersIds.add(228704281L);
		usersIds.add(40648963L);
		usersIds.add(196623213L);
		usersIds.add(147907320L);
		usersIds.add(406241541L);
		usersIds.add(117704422L);
		usersIds.add(199049235L);
		usersIds.add(375946528L);
		usersIds.add(402086251L);
		usersIds.add(6160612L);
		usersIds.add(447884472L);
		usersIds.add(416820329L);
		usersIds.add(573469697L);
		usersIds.add(432723507L);
		usersIds.add(98482144L);
		usersIds.add(69233030L);
		usersIds.add(904188878L);
		usersIds.add(16599046L);
		usersIds.add(54477592L);
		usersIds.add(403648497L);
		usersIds.add(620961074L);
		usersIds.add(547948356L);
		usersIds.add(234130252L);
		usersIds.add(59155851L);
		usersIds.add(210751309L);
		usersIds.add(376489518L);
		usersIds.add(428535922L);
		usersIds.add(250328135L);
		usersIds.add(278422925L);
		usersIds.add(408766466L);
		usersIds.add(272045581L);
		usersIds.add(257604687L);
		usersIds.add(558000189L);
		usersIds.add(317785537L);
		usersIds.add(379026534L);
		usersIds.add(332516271L);
		usersIds.add(89309212L);
		usersIds.add(149101055L);
		usersIds.add(564484623L);
		usersIds.add(158302155L);
		usersIds.add(126285114L);
		usersIds.add(90976469L);
		usersIds.add(335827702L);
		usersIds.add(263579726L);
		usersIds.add(159459351L);
		usersIds.add(72245882L);
		usersIds.add(264108533L);
		usersIds.add(20519409L);
		usersIds.add(180370504L);
		usersIds.add(81880643L);
		usersIds.add(357485483L);
		usersIds.add(418140622L);
		usersIds.add(251920096L);
		usersIds.add(24593455L);
		usersIds.add(112978948L);
		usersIds.add(774729589L);
		usersIds.add(47972261L);
		usersIds.add(273396486L);
		usersIds.add(89686415L);
		usersIds.add(140323856L);
		usersIds.add(321271223L);
		usersIds.add(828785204L);
		usersIds.add(57994130L);
		usersIds.add(124293597L);
		usersIds.add(964006116L);
		usersIds.add(436177790L);
		usersIds.add(793491536L);
		usersIds.add(106056599L);
		usersIds.add(180326197L);
		usersIds.add(214843777L);
		usersIds.add(429147891L);
		usersIds.add(190556849L);
		usersIds.add(15780526L);
		usersIds.add(531042478L);
		usersIds.add(280511665L);
		usersIds.add(545927456L);
		usersIds.add(473151993L);
		usersIds.add(374964997L);
		usersIds.add(192337254L);
		usersIds.add(403605331L);
		usersIds.add(210949202L);
		usersIds.add(312616409L);
		usersIds.add(550061193L);
		usersIds.add(197496244L);
		usersIds.add(136310951L);
		usersIds.add(13671692L);
		usersIds.add(118352409L);
		usersIds.add(412493121L);
		usersIds.add(469419845L);
		usersIds.add(424926342L);
		usersIds.add(33613252L);
		usersIds.add(8388752L);
		usersIds.add(258795277L);
		usersIds.add(424977837L);
		usersIds.add(118731516L);
		usersIds.add(23126166L);
		usersIds.add(120602403L);
		usersIds.add(494001730L);
		usersIds.add(223884023L);
		usersIds.add(231400417L);
		usersIds.add(34900631L);
		usersIds.add(403035185L);
		usersIds.add(234455061L);
		usersIds.add(214960128L);
		usersIds.add(490265400L);
		usersIds.add(256936234L);
		usersIds.add(15589380L);
		usersIds.add(7530252L);
		usersIds.add(71009163L);
		usersIds.add(489535085L);
		usersIds.add(186363147L);
		usersIds.add(492918059L);
		usersIds.add(497012393L);
		usersIds.add(128980663L);
		usersIds.add(22901424L);
		usersIds.add(791053567L);
		usersIds.add(269738033L);
		usersIds.add(245843188L);
		usersIds.add(970515612L);
		usersIds.add(236875562L);
		usersIds.add(415623321L);
		usersIds.add(336444515L);
		usersIds.add(394080644L);
		usersIds.add(376692961L);
		usersIds.add(379257140L);
		usersIds.add(186430183L);
		usersIds.add(98971248L);
		usersIds.add(160560648L);
		usersIds.add(278063725L);
		usersIds.add(190625515L);
		usersIds.add(69329030L);
		usersIds.add(87030948L);
		usersIds.add(407338020L);
		usersIds.add(574819660L);
		usersIds.add(348716582L);
		usersIds.add(728597227L);
		usersIds.add(47105050L);
		usersIds.add(51223184L);
		usersIds.add(28412592L);
		usersIds.add(105084963L);
		usersIds.add(225929405L);
		usersIds.add(493117328L);
		usersIds.add(326028296L);
		usersIds.add(50593920L);
		usersIds.add(297649434L);
		usersIds.add(71652440L);
		usersIds.add(32468042L);
		usersIds.add(223494850L);
		usersIds.add(197833600L);
		usersIds.add(414744041L);
		usersIds.add(256028347L);
		usersIds.add(247242746L);
		usersIds.add(283492691L);
		usersIds.add(237673083L);
		usersIds.add(579184353L);
		usersIds.add(18400781L);
		usersIds.add(19649135L);
		usersIds.add(492067905L);
		usersIds.add(321619705L);
		usersIds.add(297920760L);
		usersIds.add(377004643L);
		usersIds.add(82837616L);
		usersIds.add(201203913L);
		usersIds.add(116492678L);
		usersIds.add(404891102L);
		usersIds.add(146235858L);
		usersIds.add(161795148L);
		usersIds.add(818192810L);
		usersIds.add(44340136L);
		usersIds.add(177510829L);
		usersIds.add(543256414L);
		usersIds.add(112992037L);
		usersIds.add(238176244L);
		usersIds.add(18448018L);
		usersIds.add(92043007L);
		usersIds.add(67904815L);
		usersIds.add(221160169L);
		usersIds.add(625613217L);
		usersIds.add(47390948L);
		usersIds.add(449037525L);
		usersIds.add(82057416L);
		usersIds.add(559584660L);
		usersIds.add(311688094L);
		usersIds.add(83361980L);
		usersIds.add(205834147L);
		usersIds.add(113112039L);
		usersIds.add(98887175L);
		usersIds.add(274128051L);
		usersIds.add(203100477L);
		usersIds.add(164622174L);
		usersIds.add(28560233L);
		usersIds.add(201692552L);
		usersIds.add(370597479L);
		usersIds.add(74699755L);
		usersIds.add(93220223L);
		usersIds.add(26527732L);
		usersIds.add(371816589L);
		usersIds.add(312911434L);
		usersIds.add(28125526L);
		usersIds.add(178813991L);
		usersIds.add(201336218L);
		usersIds.add(793990261L);
		usersIds.add(637011482L);
		usersIds.add(598716822L);
		usersIds.add(411659349L);
		usersIds.add(377563099L);
		usersIds.add(404742240L);
		usersIds.add(13396192L);
		usersIds.add(108538164L);
		usersIds.add(126470670L);
		usersIds.add(607065583L);
		usersIds.add(29734662L);
		usersIds.add(809080424L);
		usersIds.add(259709809L);
		usersIds.add(320754311L);
		usersIds.add(504646645L);
		usersIds.add(273035975L);
		usersIds.add(479757475L);
		usersIds.add(419496756L);
		usersIds.add(412333475L);
		usersIds.add(386100785L);
		usersIds.add(216810792L);
		usersIds.add(203285503L);
		usersIds.add(540012372L);
		usersIds.add(295946543L);
		usersIds.add(279143407L);
		usersIds.add(475680918L);
		usersIds.add(109763701L);
		usersIds.add(52526819L);
		usersIds.add(151438329L);
		usersIds.add(127568291L);
		usersIds.add(198176106L);
		usersIds.add(199357039L);
		usersIds.add(781144226L);
		usersIds.add(159428650L);
		usersIds.add(10469522L);
		usersIds.add(80775888L);
		usersIds.add(138788989L);
		usersIds.add(605255891L);
		usersIds.add(180314675L);
		usersIds.add(96318661L);
		usersIds.add(429177543L);
		usersIds.add(24144462L);
		usersIds.add(78545398L);
		usersIds.add(42013583L);
		usersIds.add(81537397L);
		usersIds.add(207414538L);
		usersIds.add(54932082L);
		usersIds.add(47678136L);
		usersIds.add(118710202L);
		usersIds.add(475685652L);
		usersIds.add(916980690L);
		usersIds.add(234371743L);
		usersIds.add(461995661L);
		usersIds.add(428226051L);
		usersIds.add(42008251L);
		usersIds.add(19048014L);
		usersIds.add(17789524L);
		usersIds.add(387024110L);
		usersIds.add(172282237L);
		usersIds.add(221515237L);
		usersIds.add(372672839L);
		usersIds.add(207568720L);
		usersIds.add(249819905L);
		usersIds.add(39524905L);
		usersIds.add(406401609L);
		usersIds.add(217087919L);
		usersIds.add(317386270L);
		usersIds.add(281063032L);
		usersIds.add(18214720L);
		usersIds.add(115413350L);
		usersIds.add(524622009L);
		usersIds.add(26574847L);
		usersIds.add(17902292L);

		InAndOutDegreeFilterManager filterManager = new InAndOutDegreeFilterManager(0.1F, 0.05F);// = new InAndOutDegreeFilterManager(0.1, 0.05);
		filterManager.setGraphFacade(graphFacade);
		filterManager.setTwitterFacade(twitterFacade);
		filterManager.setSeedUsers(usersIds);

		List<Long> result = filterManager.filter();
		logger.info(result.size());
		logger.info(result);
	}

}
