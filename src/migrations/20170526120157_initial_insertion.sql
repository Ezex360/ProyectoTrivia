INSERT INTO categories (cat_name, created_at, updated_at) VALUES 
('ARTE Y LITERATURA', NOW(), NOW()),
('CIENCIA Y TECNOLOGÍA', NOW(), NOW()), 
('DEPORTES', NOW(), NOW()),
('HISTORIA', NOW(), NOW()),
('GEOGRAFIA', NOW(), NOW()),
('ENTRETENIMIENTO', NOW(), NOW());

INSERT INTO questions (category_id,question,answer1,answer2,answer3,answer4,rightans,created_at, updated_at) VALUES 
(1,' ¿Quién pintó el cuado "El jardín de las delicias"?','El Bosco','Carvaggio','Velázquez','Arcimboldo',1,NOW(),NOW()),
(1,'¿Quién vivía en el 221B de Backer Street? ','Sherlock Holmes ','Truman Capote ','Philip Marlowe ','Arthur Conan Doyle ',1,NOW(),NOW()),
(1,'¿Quién es el autor de "El retrato de Dorian Gray"? ','Oscar Wilde ','Charles Dickens ','Arthur Conan Doyle ','George Orwell ',1,NOW(),NOW()),
(1,'Gato con guantes... ','Y con botas ','No rasca bigotes ','No caza ratones ','No corre al trote ',3,NOW(),NOW()),
(1,'¿Qué forma es característica de las plantas de las iglesias románicas? ','Óvalo ','Rectángulo ','Cruz ','Cuadrado ',3,NOW(),NOW()),
(1,' ¿Qué odia Mafalda? ','El Pájaro Loco ','La sopa ','Los panqueques ','A Manolito ',2,NOW(),NOW()),
(1,'¿Quién compuso la famosa canción "Bohemian Rhapsody? ','John Lennon ','Elton John ','Freddie Mercury ','David Bowie ',3,NOW(),NOW()),
(1,'¿Qué animal quería ser domesticado por El Principito? ','Un perro  ','Un zorro ','Un gato ','Un lobo ',2,NOW(),NOW()),
(1,'¿Qué describe una prosopografía? ','El físico de una persona ','El carácter de una persona ','El fisico y el carácter de una persona ','Caricaturiza a una persona',1,NOW(),NOW()),
(1,'¿En qué siglo nació el artista conocido como Caravaggio? ','XIV ','XII ','XVI ','XVIII ',3,NOW(),NOW()),
(1,'¿Cómo se llama a la gente que no posee magia en la saga de Harry Potter? ','Humano ','Simplón ','Impuro ','Muggles ',4,NOW(),NOW()),
(1,'Arroz con leche me quiero… ','Escapar ','Matar ','Cazar ','Casar ',4,NOW(),NOW()),
(1,'¿Quién es la autora de “Los Juegos del Hambre”? ','Blue Jeans ','Suzanne Collins ','Jordi Sierra i Fabra ','Bono Bidari ',2,NOW(),NOW()),
(2,'¿Qué droga de diseño,también conocida como MDMA, es análoga a la metanfetamina? ','Polvo de ángel ','Crack ','Éxtasis  ','Popper ',3,NOW(),NOW()),
(2,'¿Cuál es la ciencia que estudia la aplicación de la informática y las comunicaciones al hogar? ','Robótica ','Domótica ','Casática ','Autología ',2,NOW(),NOW()),
(2,'¿Qué sonido hace un elefante? ','Brugen ','Gruñen ','Baritan ','Gritan ',3,NOW(),NOW()),
(2,'¿Cuál es la fórmula química del agua? ','HO ','HO2 ','H2O ','Agu ',3,NOW(),NOW()),
(2,' ¿Cómo se llama la página web más famosa en la que se puede visualizar videos de todo tipo? ','CineTube ','Glooge ','VideoTube ','YouTube',4,NOW(),NOW()),
(2,'¿Cómo se llama el sistema operativo con el que trabajan los teléfonos HTC,LG,Samsung? ','iOS ','Microsoft ','Ubuntu ','Android ',4,NOW(),NOW()),
(2,'¿Qué marca creó en 2007 el primer iPhone? ','Apple ','Nokia ','Mac ','Android ',1,NOW(),NOW()),
(2,'¿Qué animal representa al Sistema Operativo Linux? ','León ','Panda ','Pinguino  ','Leopardo ',3,NOW(),NOW()),
(2,'¿Cuál es la combinación de las teclas que copia texto en un PC? ','ctrl + p ','ctrl + c ','ctrl + q ','ctrl + v ',2,NOW(),NOW()),
(2,'¿Qué cambio de estado ocurre en la sublimación? ','De sólido a líquido ','De sólido a gaseoso ','De gaseoso a líquido ','De líquido a solido ',2,NOW(),NOW()),
(2,'¿De qué color es la sange de los peces? ','Verde oscuro ','Marrón oscuro ','Rojo ','Azul ',3,NOW(),NOW()),
(2,'¿Cuál de los siguientes órganos NO es parte del sistema inmunológico? ','Esófago ','Médula ósea ','Bazo ','Timo ',1,NOW(),NOW()),
(2,'¿Cuántas caras tiene un icosaedro? ','20 ','16 ','8 ','24 ',1,NOW(),NOW()),
(2,'¿Qué es el calostro? ','Un hueso de la espina dorsal ','Una hormona ','Una parte del intestino grueso ','La primera leche materna ',4,NOW(),NOW()),
(3,'¿De que deporte es el kemari uno de los principales antecesores?  ','Fútbol ','Ténis ','Rugby ','Karate ',1,NOW(),NOW()),
(3,' ¿Cuántas puntas de cada color hay en un tablero de backgammon? ','8 ','12 ','14 ','16 ',2,NOW(),NOW()),
(3,' ¿En qué país se inventó el voleibol? ','Gran Bretaña ','Francia ','Rusia ','Estados Unidos ',4,NOW(),NOW()),
(3,'¿Qué selección acumula mayor cantidad de expulsados en  mundiales de fútbol? ','Argentina ','Brasil ','Italia ','Camerún ',1,NOW(),NOW()),
(3,'¿Cuántos puntos vale un tiro libre encestado en baloncesto? ','Uno ','Dos ','Tres ','Depende ',1,NOW(),NOW()),
(3,'¿Cuánto dura un partido de fútbol? ','90 minutos ','45 minutos ','75 minutos','80 minutos ',1,NOW(),NOW()),
(3,'¿De qué nacionalidad es el entrenador de fútbol Tata Martino? ','Italia ','Argentina ','España ','Brasil ',2,NOW(),NOW()),
(3,'¿Cuál es el estilo de natación más rápido? ','Crol ','Espalda ','Mariposa ','Pecho ',1,NOW(),NOW()),
(3,'¿Cuántos jugadores componen un equipo de rugby? ','11 ','12 ','15 ','21 ',3,NOW(),NOW()),
(3,'¿De qué color es el cero en el cilindro de la ruleta? ','Blanco ','Negro ','Rojo ','Verde ',4,NOW(),NOW()),
(3,'¿Cuál de estas frases NO fue dicha por Diego Armando Maradona? ','Pelé, debutó con un pibe ','La pelota no dobla ','Segurola y La Habana 4310 7mo piso ','La pelota no se mancha ',2,NOW(),NOW()),
(3,'¿Dónde apoyan los jinetes sus pies? ','Montura ','Riendas ','Estribos ','Baste ',3,NOW(),NOW()),
(4,'¿En qué continente queda Chile? ','Asia ','América ','Europa ','Africa ',2,NOW(),NOW()),
(4,'¿Cuántos soldados argentinos murieron en la Guerra de las Malvinas? ','649 ','200 ','987 ','1452 ',1,NOW(),NOW()),
(4,'¿Con qué emperador estuvo casada Cleopatra? ','Ptolomeo XIV ','Julio César ','Marco Antonio ','Todas son correctas ',4,NOW(),NOW()),
(4,'El Renacimiento marcó el inicio de la Edad… ','Moderna ','Antigüedad clásica ','Contemporánea ','Media ',1,NOW(),NOW()),
(4,'¿Qué país fue dirigido por Stalin? ','Union Soviética ','Cuba ','Alemania ','Polonia ',1,NOW(),NOW()),
(4,'¿Qué se celebra el 8 de Marzo? ','El día del trabajo ','El día del medio ambiente ','El día de la mujer ','El día del niño ',3,NOW(),NOW()),
(4,'¿Quién liberó a Argentina, Chile y Perú? ','Ernesto Che Guevara ','Manual Belgrano ','José de San Martín ','Simón Bolívar ',3,NOW(),NOW()),
(4,'¿Dónde surgió la filosofía? ','Grecia ','España ','Egipto ','Japón ',1,NOW(),NOW()),
(4,'Los cuatro evangelistas de la Biblia son Mateo, Marcos, Lucas y... ','Antonio ','Jésus ','José ','Juan ',4,NOW(),NOW()),
(4,'¿De qué color es el humo que informa a los creyentes de que se ha elegido un Papa nuevo? ','Negro ','Rojo ','Amarillo ','Blanco ',4,NOW(),NOW()),
(4,'¿Maria Antonieta fue reina de qué país? ','Francia ','Nunca fue reina ','Países Bajos ','Reino Unido ',1,NOW(),NOW()),
(4,'¿En qué viaje Colón encontró a los Mayas? ','Primero ','Segundo ','Tercero ','Cuarto ',4,NOW(),NOW()),
(4,'¿Cuántos mandamientos hay en el cristianismo? ','9 ','10 ','11 ','12 ',2,NOW(),NOW()),
(4,'¿Cuál era el nombre de pila de Putin(Presidente de Rusia)? ','Vladímir ','Aléksey ','Iósif ','Aleksandr ',1,NOW(),NOW()),
(5,'¿Cuál es el código internacional para Cuba? ','CA ','CU ','CB ','Ninguna es correcta ',2,NOW(),NOW()),
(5,'¿En qué cordillera están la mayoría de las grandes montañas? ','En el Karakórum ','En el Himalaya ','En los Andes ','En las Montañas Rocosas ',2,NOW(),NOW()),
(5,'¿Entre qué dos países está el lago Titicaca?  ','Bolivia y Perú ','Bolivia y Paraguay ','Bolivia y Brasil ','Bolivia y Argentina ',1,NOW(),NOW()),
(5,'¿Cuál es la religión mayoritaria en China? ','Budismo ','Taoísmo ','Confucianismo ','Cristianismo ',1,NOW(),NOW()),
(5,'¿Cuál de las grandes montañas ha sido estalada más veces? ','El K2 ','El Everest ','El Annapurna I ','El Annapurna II ',2,NOW(),NOW()),
(5,'¿A qué país pertenece la isla de Tasmania? ','Estados Unidos ','Australia ','Portugal ','Ninguna es correcta ',2,NOW(),NOW()),
(5,'¿En cuál de los siguientes países NO hay ningún desierto? ','España ','Chile ','Mongolia ','Alemania ',4,NOW(),NOW()),
(5,'¿Cuál es principal celebración holandesa? ','Navidad ','La llegada del verano ','El día de la Reina ','Hallowen ',3,NOW(),NOW()),
(5,'¿Cuál de las siguientes especialidades NO es típica de la cocina estadounidense? ','La hamburguesa ','El pastel de cangrejo ','La tarta de manzana ','Todas son típicas',4,NOW(),NOW()),
(5,' ¿Con cuántos países limita Argentina? ','Tres ','Cuatro ','Cinco ','Seis ',3,NOW(),NOW()),
(5,'¿Cuál es la capital de Japón? ','Tokio ','Kyoto ','Pekin ','Ninguna es correcta ',1,NOW(),NOW()),
(5,'¿Dónde creció Sigmund Freud ? ','París ','Munich ','Kiev ','Viena ',4,NOW(),NOW()),
(6,'¿Por cuál de estas películas ganó Clint Eastwood el premio Oscar al mejor director? ','Million Dollar Baby ','Cartas desde Iwo Jima ','Mystic River','Los puentes de Madison ',4,NOW(),NOW()),
(6,'¿Cómo se llamaba el personaje que interpretaba Al Pacino en "Scarface"? ','Sonny Montana ','Tony Montana ','Michael Corleone ','Frank Slade',2,NOW(),NOW()),
(6,'¿Cuál es considerado por los fans el peor juego de la historia de ATARI? ','Tetris ','ET ','Combat ','Galaxian ',2,NOW(),NOW()),
(6,'¿A qué película de Disney pertenece la canción "Un mundo ideal"? ','Aladdín ','Pocahontas ','Mulán ','Hércules ',1,NOW(),NOW()),
(6,'¿A quién se considera el Rey del Pop? ','Justin Bieber ','Michael Jackson ','Zayn Malik ','Zac Efron ',2,NOW(),NOW()),
(6,' ¿Qué personaje del videojuego Mortal Kombat tiene poderes de hielo? ','Sub-Zero ','Scorpion ','Reptile ','Motaro ',1,NOW(),NOW()),
(6,' ¿Qué día es San Valentín?','14 de Marzo  ','5 de Febrero  ','14 de Febrero ','15 de Marzo ',3,NOW(),NOW()),
(6,'¿Cómo se llama el actor protagonista de "Mi pobre Angelito"? ','Macaulay Culkin ','Keanu Reeves ','Johnny Depp ','Leonardo DiCaprio ',1,NOW(),NOW()),
(6,'¿Cómo se llama el pájaro símbolo de los Juegos del Hambre? ','Lechuza ','Sinsajo ','Gale ','Llamas  ',2,NOW(),NOW()),
(6,'¿Cuántos colores tiene un cubo de Rubik clásico? ','4 ','6 ','8 ','9 ',2,NOW(),NOW()),
(6,'Arroz con leche me quiero casar con una señorita de… ','San Nicolás ','San Martin ','San Justo ','San José ',1,NOW(),NOW()),
(6,'¿Quién es la mascota de SEGA? ','Sonic ','Mario ','Pac Man ','Ryu ',1,NOW(),NOW()),
(6,'¿Cómo se llama el protagonista de la saga Indiana Jones? ','Jack Nicholson ','Michael Fox ','Harrison Ford ','Robin Williams ',3,NOW(),NOW()),
(6,'¿Quién canta ” Vivir mi vida”? ','Pitbull ','Marc Anthony ','Jay Z ','Chris Brown ',2,NOW(),NOW()),
(6,'¿Qué personaje de Disney perdió su zapato de cristal ? ','Tiana ','Blancanieves ','La Sirenita ','Cenicienta ',4,NOW(),NOW()),
(1,' Los amantes de Teruel… ','Viajaban en carrusel ','Tonta ella y tonto él ','Vivían en un burdel  ','Existieron en papel ',2, NOW(), NOW()),
(1,'Quién es el autor de “Moby Dick”? ','Herman Melville ','Henry David Thoreau ','Ralph Waldo Emerson ','Henry James',1, NOW(), NOW()),
(1,'¿Quién pintó La Capilla Sixtina? ','Giorgio Vasari ','Leonardo Da Vinci ','Miguel Ángel ','Tiziano',3, NOW(), NOW()),
(1,'¿Qué filósofo dijo ‘solo sé que no sé nada’? ','Heráclito ','Platón ','Sócrates ','Tales de Mileto ',3, NOW(), NOW()),
(1,'¿Cómo despertó el principe a la Bella Durmiente? ','Tocando palmas ','Acariciándola ','Besándola ','Gritándole ',3, NOW(), NOW()),
(2,' ¿Qué cambio de estado ocurre en la sublimación? ','De sólido a líquido ','De sólido a gaseoso ','De gaseoso a líquido ','De líquido a solido ',2, NOW(), NOW()),
(2,'¿Cuántas cavidades estomacales tiene una vaca? ','Una ','Dos ','Tres ','Cuatro ',4, NOW(), NOW()),
(2,'¿Por qué los cocodrilos mantienen la boca abierta durante largo rato? ','Para calentarse ','Para hacer la digestión ','Para beber agua ','Por si se cuela algo que puedan comerse',1, NOW(), NOW()),
(2,'¿Como debería estar una persona para que le fuera practicada una autopsia?  ','Sedada ','En coma ','Muerta ','Dormida ',3, NOW(), NOW()),
(2,'¿Cuántos colores tenía la primera tarjeta gráfica? ','1 ','2 ','8 ','16 ',2, NOW(), NOW()),
(3,'¿Quién inventó el arte marcial llamado Jeet Kune Do? ','David Carradine ','Bruce Lee ','Kato Mimoko ','Ninguna es correcta ',2, NOW(), NOW()),
(3,'¿De qué deporte es el longboard una de las modalidades? ','Skateboard ','Snowboard ','Alpinismo ','Surf ',1, NOW(), NOW()),
(3,'¿Qué tipo de competición es el Giro de Italia? ','Una carrera ciclista ','Una competición de vela ','Un maratón ','Una carrera automovilística ',1, NOW(), NOW()),
(3,'¿A qué tipo de billa se juega con más bolas? ','Snooker ','Bola 9 ','Billar español ','Billar italiano ',1, NOW(), NOW()),
(3,' ¿Dónde se inventó el tenis de mesa? ','China ','Japón ','Corea del Sur ','Inglaterra ',4, NOW(), NOW()),
(4,'¿Quién pronunció la frase: “Bebamos de la copa de la destrucción”? ','Margaret Tatcher ','Mussolini ','Gengis Kan ','Berlusconi ',3, NOW(), NOW()),
(4,'¿Cómo se llama la capital del antiguo imperio azteca? ','Quetzalcoatl ','Tenochtitlan ','Culhuacan ','Texcoco ',2, NOW(), NOW()),
(4,'¿Quién fue Bobby Fischer? ','Un futbolista histórico ','Una leyenda del ajedrez ','El inventor del Fisch ','Un filósofo norteamericano',2, NOW(), NOW()),
(4,' ¿Cómo se llama la casilla del centinela? ','Casita ','Garita ','Pecera ','Cabina ',2, NOW(), NOW()),
(4,'¿En qué idioma se escribió el antiguo testamento por primera vez? ','Hebreo ','Griego ','Latín','Jesuíta ',1, NOW(), NOW()),
(5,'¿Dónde está la montaña de Jade? ','China ','Corea del Sur ','Taiwan ','Singapur ',3, NOW(), NOW()),
(5,'¿Cuál es el país más pequeño de Asia? ','Maldivas ','Nauru ','Laos ','Japón ',1, NOW(), NOW()),
(5,'¿De qué país es el panda el animal nacional ?  ','Canadá ','Tanzania ','China ','Australia ',3, NOW(), NOW()),
(5,' ¿Cuál es la capital de Islandia? ','Riga ','Reikjavik ','Dublín ','Minsk ',2, NOW(), NOW()),
(5,'¿Cuántas estrellas tiene la bandera de China? ','Ninguna es correcta ','6 ','0 ','5 ',4, NOW(), NOW()),
(6,'¿Cómo se llamaba el personaje que interpretaba John Travolta en “Grease”?  ','Danny Puño ','Danny Zuko ','Danny Grease ','Danny Chulo ',2, NOW(), NOW()),
(6,'¿En qué año se estrenó la película de Disney “Pinocho”?  ','1940 ','1950 ','1952 ','1946 ',1, NOW(), NOW()),
(6,'¿Qué actor interpretaba a “Hache” en la película 3MSC? ','Hugo Silva ','Miguel Angel Silvestre ','Dani Martín ','Mario Casas ',4, NOW(), NOW()),
(6,'¿Qué personaje de Disney perdió su zapato de cristal? ','Tiana ','Blancanieves ','La Sirenita ','Cenicienta',4, NOW(), NOW()),
(6,'¿Cómo se llama la serie de dibujos animados en la que sale un gato cósmico del siglo XXI? ','Doraemon ','Bob Esponja ','Las tortugas ninja ','Las Supernenas ',1, NOW(), NOW()),
(6,'¿Qué Beatle fue asesinado por un fan? ','George Harrison ','Ringo Star ','Ninguno ','Jonh Lennon ',4, NOW(), NOW());



