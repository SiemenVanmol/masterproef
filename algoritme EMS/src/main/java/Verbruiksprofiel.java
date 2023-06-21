import java.sql.Timestamp;
import java.util.*;


public class Verbruiksprofiel {
    static float[] Monday = new float[]{0.0000178602429258237f, 0.0000172332448400944f, 0.0000166454150778771f, 0.0000161368165845102f, 0.0000157289729864685f, 0.0000153855313254742f, 0.0000151391984211527f, 0.0000149346112827135f, 0.000014774308718348f, 0.0000146484399014872f, 0.0000145652464058889f, 0.000014525296556024f, 0.0000145614893771687f, 0.0000145916445477241f, 0.0000147229796668732f, 0.0000148543410339f, 0.0000151701555084317f, 0.000015529140299683f, 0.0000152927440827186f, 0.0000165794518565211f, 0.0000177286124277236f, 0.0000187750057131046f, 0.0000199352060470045f, 0.000021103023447669f, 0.0000221453944741889f, 0.0000231117854557382f, 0.0000237101275491135f, 0.0000240700208883198f, 0.0000244409735799659f, 0.0000247219569423692f, 0.0000251878860541602f, 0.000025690052478507f, 0.0000260834147931066f, 0.0000263979305557299f, 0.0000266204935494347f, 0.0000267902742912294f, 0.0000270238296702931f, 0.0000269885344948268f, 0.0000269958641643211f, 0.0000270715778415021f, 0.0000273977297686679f, 0.0000277666549190681f, 0.0000282735311360704f, 0.0000285013993809912f, 0.0000280008649914945f, 0.0000274144777286885f, 0.0000268865289710617f, 0.0000265595811395643f, 0.0000266847642911955f, 0.0000263809275183227f, 0.0000261803715393791f, 0.0000259248652888097f, 0.0000261231117778733f, 0.0000258563218275382f, 0.0000255818241037891f, 0.0000253627742367422f, 0.0000254149610871565f, 0.0000252817828349551f, 0.0000252378979521941f, 0.0000253521182625228f, 0.0000257052009113142f, 0.0000260904189178458f, 0.000026638431297062f, 0.0000274082936579275f, 0.0000282174524084617f, 0.0000290734581395455f, 0.0000301181036431965f, 0.0000310894736084007f, 0.0000315755810682543f, 0.0000318125625222246f, 0.0000319043286944601f, 0.0000317928430872274f, 0.0000315686770409381f, 0.0000310795940993914f, 0.0000305059039517466f, 0.00003008295967288f, 0.0000296062288815487f, 0.0000290958667395411f, 0.0000286914626328243f, 0.0000282693893143285f, 0.0000286105320888589f, 0.0000286425254951791f, 0.0000275395932713668f, 0.0000275457689306528f, 0.0000286775652539375f, 0.0000286420375697123f, 0.0000283527599415706f, 0.000027459752340206f, 0.000026711242311922f, 0.0000253738067231475f, 0.000023978849391315f, 0.0000227525584863316f, 0.0000215422002412689f, 0.0000203012640612483f, 0.0000194827667692509f, 0.0000186470766855319f};
    static float[] Tuesday = new float[]{0.0000181138462945792f, 0.0000173974291951777f, 0.0000167429901912074f, 0.0000161796530252456f, 0.0000158107551008838f, 0.0000154548423683153f, 0.000015197824423396f, 0.0000149934301394475f, 0.0000148498238458761f, 0.0000147359487941576f, 0.0000146638771632116f, 0.0000146421706709542f, 0.0000146760682374177f, 0.0000147324923013122f, 0.0000148983418313613f, 0.0000150481603052734f, 0.0000154016765542112f, 0.0000157373329487044f, 0.0000155827725292484f, 0.0000168176794167978f, 0.0000179319227959907f, 0.0000189849218298386f, 0.00002014785863928f, 0.0000213218671021831f, 0.0000223276781038076f, 0.0000232874268676891f, 0.0000238649072446136f, 0.000024191223513611f, 0.0000245127665398168f, 0.0000247274854544437f, 0.0000251312370713852f, 0.0000255913506526266f, 0.0000259566890959503f, 0.0000262188035809568f, 0.0000264146694885338f, 0.0000265625335351654f, 0.0000267890625014342f, 0.0000267159924364844f, 0.0000267287750274307f, 0.000026792294228346f, 0.0000271280651625413f, 0.0000274804392592884f, 0.0000279606879838829f, 0.0000282002225110509f, 0.0000276331746598137f, 0.0000270398408087562f, 0.0000264880577029053f, 0.0000261464339138264f, 0.0000263358705097034f, 0.0000260794613546813f, 0.0000259527249393123f, 0.000025688141133629f, 0.0000257858053706691f, 0.0000255609704611582f, 0.0000253502410885595f, 0.0000251735717583867f, 0.0000252410521698109f, 0.000025091994925743f, 0.0000250492529267209f, 0.0000252200630505272f, 0.0000256561696525722f, 0.0000260213165734281f, 0.0000265424019613689f, 0.0000273092289902756f, 0.0000280812173994899f, 0.000028967866911446f, 0.0000299892251359346f, 0.0000309343071195327f, 0.0000314105613031804f, 0.0000315936849983806f, 0.0000316380048648256f, 0.0000315117595412649f, 0.0000312612661380248f, 0.0000307923472430783f, 0.0000302626537536126f, 0.0000298650970556766f, 0.0000294133891229865f, 0.0000289403748992136f, 0.000028542215531449f, 0.0000281975650928789f, 0.0000284441027722078f, 0.0000285578849962005f, 0.0000276585633185268f, 0.0000274711666437183f, 0.0000286712698774853f, 0.0000287088102627473f, 0.0000284371063759285f, 0.000027551362842774f, 0.000026770749624868f, 0.0000254454147332387f, 0.0000240716844659936f, 0.0000228532462260366f, 0.0000217432593532833f, 0.0000204911571257071f, 0.0000196616195217166f, 0.0000188142532783403f};
    static float[] Wednesday = new float[]{0.0000182721564936508f, 0.000017543479003429f, 0.000016868519620211f, 0.0000162925292486522f, 0.0000159289818235603f, 0.0000155658843298022f, 0.0000152958104976047f, 0.0000150848449080592f, 0.0000149366716253499f, 0.0000148135164364614f, 0.0000147325537639705f, 0.0000147016642100745f, 0.0000147376149972059f, 0.0000147860561607362f, 0.0000149447224018771f, 0.000015091582099333f, 0.0000154682444467556f, 0.000015739399468765f, 0.0000156565730410008f, 0.0000168124106671393f, 0.0000178605639568921f, 0.000018833199866625f, 0.0000199394177494998f, 0.0000210843879962139f, 0.0000221080393803799f, 0.0000231192704102485f, 0.0000238077803638013f, 0.0000242573751421603f, 0.0000246808984526334f, 0.000024985804765512f, 0.000025432382474553f, 0.0000259210312177228f, 0.0000263180831959721f, 0.0000265988703831194f, 0.0000268150483335345f, 0.0000269909964141676f, 0.0000272660190544161f, 0.0000272317648485339f, 0.0000272467366070629f, 0.0000273481922842474f, 0.0000276795668396922f, 0.0000280408912141158f, 0.0000285191640822997f, 0.0000288039387089103f, 0.0000282834297759139f, 0.0000279094393075562f, 0.0000275791483345581f, 0.0000272505414883611f, 0.0000274926060759532f, 0.0000272003797833761f, 0.0000270489843140721f, 0.0000267015737062242f, 0.0000267864461274218f, 0.0000265346701502909f, 0.0000262688556635717f, 0.0000261053377704653f, 0.0000261781631203004f, 0.0000261257759711888f, 0.0000260592451156247f, 0.0000260769453362036f, 0.0000263298241706292f, 0.0000265786033011626f, 0.0000269777442600327f, 0.0000275929127820107f, 0.0000282458935814593f, 0.0000290552065753129f, 0.0000300406533115083f, 0.0000309761414152106f, 0.0000313467741778929f, 0.0000315500219529828f, 0.0000316078460706705f, 0.0000314990168300999f, 0.0000313492382144843f, 0.0000309033681610079f, 0.0000303966863832514f, 0.0000300266885732159f, 0.0000296268833792187f, 0.0000291555320244661f, 0.0000287539574223222f, 0.0000284382223955503f, 0.0000286285175614353f, 0.0000287908936273763f, 0.0000279678664058063f, 0.0000276560783281113f, 0.0000288546244777402f, 0.0000289218982583724f, 0.0000286452303638984f, 0.0000277678086392319f, 0.000026998414843138f, 0.0000256734528508927f, 0.0000242877411685513f, 0.0000230628879016799f, 0.0000217896552717481f, 0.0000205377414517401f, 0.0000197036194695515f, 0.0000188594489335598f,};
    static float[] Thursday = new float[]{0.0000182823125856339f, 0.0000175564107735219f, 0.0000168752333457836f, 0.0000163026277241151f, 0.0000159272745904512f, 0.0000155576969754088f, 0.0000152921405722182f, 0.0000150786853384738f, 0.000014915113214553f, 0.0000147888986639711f, 0.000014709909106639f, 0.000014682083518502f, 0.0000147157516522301f, 0.0000147695077705628f, 0.0000149322435888703f, 0.0000150793286078594f, 0.0000155153335010362f, 0.0000157007880337487f, 0.0000158105221692569f, 0.0000168961448729924f, 0.0000179770880752393f, 0.0000190058136668699f, 0.0000201649089718934f, 0.0000213475394143872f, 0.0000223769538491094f, 0.0000233406759392881f, 0.0000239508544474758f, 0.0000243071765024805f, 0.0000246420757980025f, 0.0000248555339647369f, 0.0000252668457111748f, 0.0000257180987332104f, 0.0000260941269921962f, 0.0000263517719749814f, 0.0000265259325988134f, 0.0000266809307341631f, 0.0000269220070998931f, 0.0000268664855619467f, 0.0000268824072778364f, 0.0000269683978509824f, 0.0000272873232571548f, 0.0000276278137310321f, 0.0000281156873769364f, 0.0000283528829956861f, 0.0000278354775595692f, 0.0000272803678234161f, 0.0000267442730014074f, 0.0000263886574597454f, 0.0000267046850016252f, 0.0000264747686612697f, 0.0000263261389820394f, 0.0000260385801023712f, 0.0000260583237017217f, 0.0000258152116157212f, 0.0000255816912441003f, 0.0000253760106700588f, 0.0000253981084927758f, 0.000025236751716783f, 0.0000251712162027657f, 0.0000253264834778245f, 0.0000258387023221616f, 0.0000261842594891045f, 0.000026687793270043f, 0.000027383114332768f, 0.0000280719302343574f, 0.0000288761634808422f, 0.0000298313738206313f, 0.0000307261307108646f, 0.00003110619459401f, 0.0000312651245095594f, 0.0000313055724460298f, 0.0000311616003016857f, 0.0000309497831926445f, 0.0000305205712603658f, 0.0000300299420184401f, 0.0000296751017240093f, 0.0000292694089079245f, 0.0000288244132615911f, 0.0000284750201360628f, 0.0000281786956515525f, 0.0000283526202084164f, 0.0000285814329639215f, 0.0000278334016465433f, 0.0000274039101804722f, 0.0000286183413658858f, 0.0000287536225094008f, 0.0000285147396441946f, 0.0000276728173919901f, 0.0000269108164915333f, 0.0000256092972710099f, 0.0000242497453084541f, 0.0000230366186630065f, 0.0000218330370745416f, 0.0000205942738496374f, 0.0000197748673416473f, 0.0000189353399504042f,};
    static float[] Friday = new float[]{0.0000183759623576639f, 0.0000176454691666022f, 0.0000169593430407517f, 0.0000163749914917741f, 0.0000159861512255369f, 0.0000156103432605759f, 0.000015330457101761f, 0.000015107164597211f, 0.0000149613845565767f, 0.0000148314880052246f, 0.0000147447830466581f, 0.0000147054285827364f, 0.000014727991476871f, 0.0000147737109644605f, 0.0000149225198652277f, 0.0000150553019127073f, 0.0000155037947864522f, 0.0000156197356296763f, 0.00001580162545443f, 0.0000168057206690404f, 0.0000178402407630196f, 0.0000188228571295094f, 0.0000199540018680089f, 0.0000211222470098154f, 0.0000221990331203467f, 0.0000232395223487293f, 0.0000239090477333576f, 0.0000243386340024431f, 0.0000247354863367406f, 0.0000250138548032935f, 0.0000254764029852115f, 0.0000259618406404541f, 0.0000263855040161924f, 0.0000266667640741938f, 0.0000268716739960549f, 0.0000270195230064497f, 0.0000272862825804943f, 0.000027220552374542f, 0.000027220153738433f, 0.000027270173905022f, 0.0000275944775086321f, 0.0000279122864385186f, 0.0000283399099329371f, 0.0000285599567213356f, 0.0000280475357180742f, 0.000027519712886504f, 0.000027026520163823f, 0.000026695792947637f, 0.0000270948558528703f, 0.0000268486546596995f, 0.0000267263669573856f, 0.0000264859492179211f, 0.0000263978720707064f, 0.0000261634040272157f, 0.0000259182774375154f, 0.0000256832508045768f, 0.0000255872578452267f, 0.0000254386568802935f, 0.0000253648837661777f, 0.0000254851692199148f, 0.0000259043964253883f, 0.0000261434393685887f, 0.0000265316935616506f, 0.0000270853410344877f, 0.0000276435438390577f, 0.0000282330377725595f, 0.0000289918756991045f, 0.0000296479835462189f, 0.0000298773031963059f, 0.0000299671206817299f, 0.0000299505447974356f, 0.0000297966295351491f, 0.0000296648013966188f, 0.0000293104349068523f, 0.0000288854661115395f, 0.0000285719492485488f, 0.0000282393859287334f, 0.0000278964739285076f, 0.0000276446698558841f, 0.0000274521011620886f, 0.0000275880251406003f, 0.0000279497390974074f, 0.0000274593398503453f, 0.0000269313468292411f, 0.0000283474665851525f, 0.0000286917796718607f, 0.0000286197902847856f, 0.0000279691074422702f, 0.0000274798443579409f, 0.0000263432982074676f, 0.0000251397742150244f, 0.0000240196948873808f, 0.0000228570545472517f, 0.0000216630223718416f, 0.0000208245824035976f, 0.0000199711715379841f,};
    static float[] Saterday = new float[]{ 0.0000194276457549604f, 0.0000186739855540077f, 0.0000179438198820641f, 0.0000173085736906787f, 0.000016877209090708f, 0.0000164478721843734f, 0.0000161051212117706f, 0.0000158251018127649f, 0.0000156033470937539f, 0.0000154184682616963f, 0.0000152718032722301f, 0.0000151734289727248f, 0.000015099438198316f, 0.0000150741385992994f, 0.0000151058264971175f, 0.0000151229029693536f, 0.0000153905559656482f, 0.0000152869981877602f, 0.0000152923672302193f, 0.0000159147018129718f, 0.0000164288415372517f, 0.0000168468200331386f, 0.0000173029593438545f, 0.0000177949534625893f, 0.0000184842805909994f, 0.0000192675922540403f, 0.0000201924050522756f, 0.0000211916251109157f, 0.0000223814257011962f, 0.0000235219636314314f, 0.0000245368980644125f, 0.0000254657832980668f, 0.000026430904505973f, 0.0000272094506140124f, 0.0000279170466128436f, 0.0000284910737170858f, 0.0000291170383030713f, 0.0000293159286357779f, 0.0000294020259896901f, 0.000029529182083383f, 0.0000298790580888978f, 0.0000302087229618122f, 0.0000307113413614851f, 0.000031160821557381f, 0.000030899681807534f, 0.0000304734141537419f, 0.000029982273840607f, 0.0000295747429827209f, 0.0000297020037467728f, 0.0000292576409844053f, 0.0000288931787259023f, 0.00002849618872906f, 0.0000283200792392273f, 0.0000279623716861787f, 0.0000276169397994846f, 0.0000273210258728031f, 0.0000272510627249078f, 0.0000270784714228508f, 0.000026888793949646f, 0.0000267714252200314f, 0.0000267770283357846f, 0.0000266591218795357f, 0.0000268379855842478f, 0.000027193373936591f, 0.0000278639294282982f, 0.0000284574765421299f, 0.0000290720523388334f, 0.0000297072587211065f, 0.0000300794707164498f, 0.000030330547351402f, 0.0000304637304180694f, 0.000030500349275439f, 0.0000304181237586642f, 0.0000300594556088898f, 0.0000296449825339267f, 0.0000293249974933315f, 0.0000290008416556952f, 0.0000286881891386764f, 0.000028421854076674f, 0.0000280866308985373f, 0.0000279653310768198f, 0.000028231882962887f, 0.0000277812140201321f, 0.0000268851514561123f, 0.0000274386615564755f, 0.0000275639180521564f, 0.0000271935000428906f, 0.0000266519872204098f, 0.0000261616076612253f, 0.0000252622975802099f, 0.0000242921701431934f, 0.0000233645954847539f, 0.0000224580622012162f, 0.0000214146827516603f, 0.0000207004850577618f, 0.0000199521155855519f,};
    static float[] Sunday = new float[]{0.0000194828785372007f, 0.0000187867652095918f, 0.0000180942723208709f, 0.0000174908338518362f, 0.0000170638585120518f, 0.0000166398187243433f, 0.0000162966866221484f, 0.0000160096272119189f, 0.000015784295336795f, 0.000015580125059870f, 0.0000154033197952095f, 0.0000152695445778668f, 0.000015171386464293f, 0.0000151023419481727f, 0.0000150793227571534f, 0.0000150298421881241f, 0.0000152192149126585f, 0.0000149951598306509f, 0.0000150008291752296f, 0.0000155016472407517f, 0.0000158522913271264f, 0.0000161347409445948f, 0.0000164335171371367f, 0.0000167414508875844f, 0.0000172227348073296f, 0.0000178321738763041f, 0.0000186044277529476f, 0.0000194869273956458f, 0.0000205657550211797f, 0.0000216516671362441f, 0.0000226179144223616f, 0.0000235317145950114f, 0.0000244124526417383f, 0.0000253013655934195f, 0.0000260108083998874f, 0.0000266700859368557f, 0.0000273312562596297f, 0.0000277189550517898f, 0.0000280053629394486f, 0.0000282739479070517f, 0.0000287904777834005f, 0.0000293038068338723f, 0.0000299292958054021f, 0.000030552069899223f, 0.00003056618356814f, 0.0000302397173187532f, 0.0000298067773450934f, 0.0000293453326239813f, 0.0000292252046197531f, 0.0000285386796719955f, 0.000027934562359264f, 0.000027333518517901f, 0.000027020692882786f, 0.0000265262312742076f, 0.0000260959167875971f, 0.0000257130354970542f, 0.0000255420822170416f, 0.0000252754037102715f, 0.0000250509764258856f, 0.0000248708458078684f, 0.0000248713417769908f, 0.0000248237981559935f, 0.0000249863029976041f, 0.0000253655766385865f, 0.0000259837406717152f, 0.0000266042511889572f, 0.000027329556001475f, 0.0000280890431071701f, 0.0000287802400331429f, 0.0000293167431918099f, 0.0000297400500543878f, 0.0000300150253401534f, 0.0000300916851825319f, 0.0000299095433905402f, 0.00002964467596876f, 0.0000294634507972552f, 0.000029263144441233f, 0.000029041501546076f, 0.0000288637717809762f, 0.0000286358041562656f, 0.0000284759531928015f, 0.000028745938270837f, 0.0000283088937571726f, 0.0000272431166229229f, 0.0000277256199395769f, 0.0000277088392657135f, 0.0000271319275991412f, 0.0000263272868196299f, 0.000025531487494592f, 0.0000243672308983331f, 0.0000231577297702278f, 0.0000219184170325033f, 0.0000212223827750881f, 0.0000201439013325536f, 0.0000194958294346866f, 0.000018808948650079f};


//        Date date = new Date();
//        var dayValues = dayValues(date, 4000);
//        System.out.println(dayValues);
//        var komendeUren  = komendeUren(dayValues, date);
//        System.out.println(komendeUren);
//         boolean komtErEenPiek = komtEreenVerbruikspiek(komendeUren);
//         System.out.println(komtErEenPiek);
//        boolean isErEenPiek = isEreenVerbruikspiek(komendeUren);
//        System.out.println(isErEenPiek);

    public  LinkedHashMap<Date, Float> dayValues( Date now, int jaarverbruik){
        float[] values = numberToDayName(now.getDay());
        LinkedHashMap<Date, Float> dayValues = new LinkedHashMap<Date,Float>();
        int year = now.getYear();
        int month = now.getMonth();
        int day = now.getDate();
        int k = 0;
        for(int i=0; i< 24; i++){
            int hour = i;
            int min = 0;
            for(int j = 0; j < 4;j++){
                Date date1 = new Date(year, month, day, hour, min, 00);
                dayValues.put(date1,values[k]* jaarverbruik);
                k++;
                min = min +15;
            }
        }

        return dayValues;
    }
    public  LinkedHashMap<Date, Float> verbruikVoorAuto(Date now, Date vertrekuur, int verbruik){
        LinkedHashMap<Date, Float> dayValuesToday = dayValues(now, verbruik);
        LinkedHashMap<Date, Float> dayValuesTomorrow = dayValues(vertrekuur, verbruik);
        for(int i = 0; i < 18; i++){
            for (int j = 0; j<4;j++) {
                int minutes = j*15;
                int hour = i;
                Date date1 = new Date(now.getYear(), now.getMonth(), now.getDate(), hour, minutes, 00);
                dayValuesToday.remove(date1);
            }
        }
        for (int k = vertrekuur.getHours(); k<24; k++){
            for (int p = 0; p<4;p++) {
                int minutes = p*15;
                int hour = k;
                Date date2 = new Date(vertrekuur.getYear(), vertrekuur.getMonth(), vertrekuur.getDate(), hour, minutes, 00);
                dayValuesTomorrow.remove(date2);
            }
        }
        List<Date> keys = new ArrayList<Date>(dayValuesTomorrow.keySet());
        for(int o = 0; o< dayValuesTomorrow.size(); o++ ){
            dayValuesToday.put(keys.get(o),Float.valueOf(dayValuesTomorrow.get(keys.get(0))));
        }
        return dayValuesToday;
    }
    public  float[] numberToDayName( int day){
        if( day == 0){
            return Sunday;
        }
        if( day == 1){
            return Monday;
        }
        if( day == 2){
            return Tuesday;
        }
        if( day == 3){
            return Wednesday;
        }
        if( day == 4){
            return Thursday;
        }
        if( day == 5){
            return Friday;
        }
        return Saterday;
    }

    public   LinkedHashMap<Date, Float> komendeUren(LinkedHashMap<Date, Float> dayValues, Date now){
        int beginUur = now.getHours() ;
        int year = now.getYear();
        int month = now.getMonth();
        int day = now.getDate();
        for(int i=0; i< beginUur; i++){
            int hour = i;
            int minutes = 0;
            for(int j = 0; j < 4;j++){
                Date date1 = new Date(year, month, day, hour, minutes, 00);
                dayValues.remove(date1);
                minutes = minutes +15;
            }
        }
        return dayValues;
    }
    public float verwachtVerbruik (LinkedHashMap<Date, Float> dayValues){
        float result = 0;
        for (Float values : dayValues.values()) {
            float floatValue = values.floatValue();
            result += floatValue;
        }
        return result;

    }
    public  boolean komtEreenVerbruikspiek(LinkedHashMap<Date, Float> dayValues){ // komendeUren gebruiken
        float max = Collections.max( dayValues.values());
        float min = Collections.min(dayValues.values());
        ArrayList<Float> newList = new ArrayList<>(dayValues.values());
        float range = max - min;
        float referenceValue = min + 0.8f*range; // referentiewaarde ligt op 20% van max waarde.


        for( int i = 4; i< dayValues.values().size(); i++){
            if(newList.get(i)> referenceValue){
                return true;
            }
        }
        return false;
    }
    public  boolean isEreenVerbruikspiek(LinkedHashMap<Date, Float> dayValues){ // komendeUren gebruiken
        float max = Collections.max( dayValues.values());
        float min = Collections.min(dayValues.values());
        ArrayList<Float> newList = new ArrayList<>(dayValues.values());
        float range = max - min;
        float referenceValue = min + 0.8f*range; // referentiewaarde ligt op 20% van max waarde.

        for( int i = 0; i< 4; i++){

            if(newList.get(i)> referenceValue){// checkt per uur
                return true;
            }
        }
        return false;
    }


}
