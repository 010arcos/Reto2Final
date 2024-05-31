create database AjedrezOpen;
use AjedrezOpen;

create table jugador(
    rankingI int,
    rankingF int,
    titulo varchar(5),
    nombre varchar(50),
    federacion varchar(20),
    fide int,
    fideID varchar(30),
    hotel boolean default false,
    CV boolean default false,
    importeP int, /*esta no haría falta pero podemos meter el importe del premio que se lleva el jugador*/
    categoria enum('A', 'B'),
    descalificado boolean default false,
    primary key(rankingI, categoria)
);

create table torneo(
    categoria enum('A', 'B') primary key
);

alter table jugador add constraint fk_jugador_categoria foreign key (categoria) references torneo(categoria) on delete cascade on update cascade;

create table premio(
    tipo varchar(30),
    categoria enum('A', 'B'),
    puesto int, 
    importe int,
    rankingI int,
    primary key (tipo, categoria, puesto),
    constraint fk_premio_categoria foreign key (categoria) references torneo (categoria) on delete cascade on update cascade,
    constraint fk_premio_rankingI foreign key (rankingI) references jugador(rankingI) on delete cascade on update cascade
);

create table optarPremio(
    rankingI int,
    categoria enum('A', 'B'),
    tipo varchar(30),
    puesto int,
    constraint fk_optarPremio_jugador foreign key (rankingI, categoria) references jugador(rankingI, categoria) on delete cascade on update cascade,
    constraint fk_optarPremio_premio foreign key (tipo, categoria, puesto) references premio(tipo, categoria, puesto) on delete cascade on update cascade,
    primary key (rankingI, categoria, tipo, puesto)
);

INSERT INTO torneo (categoria) VALUES ('A'), ('B');

ALTER TABLE premio ADD COLUMN prioridad INT;
alter table premio add column nombre varchar(50);

UPDATE premio SET prioridad = 1 WHERE tipo = 'general';
UPDATE premio SET prioridad = 2 WHERE tipo = 'sub2400';
UPDATE premio SET prioridad = 3 WHERE tipo = 'sub2200';
UPDATE premio SET prioridad = 4 WHERE tipo = 'sub1800';
UPDATE premio SET prioridad = 5 WHERE tipo = 'sub1600';
UPDATE premio SET prioridad = 6 WHERE tipo = 'sub1400';
UPDATE premio SET prioridad = 7 WHERE tipo = 'cv';
UPDATE premio SET prioridad = 8 WHERE tipo = 'h';




insert into premio (categoria, tipo, puesto, importe) values 
/* PREMIOS OPEN A */
/* General */
('A', "general", 1, 2300),
('A', "general", 2, 1200),
('A', "general", 3, 650),
('A', "general", 4, 550),
('A', "general", 5, 500),
('A', "general", 6, 400),
('A', "general", 7, 300),
('A', "general", 8, 250),
('A', "general", 9, 200),
('A', "general", 10, 150),
('A', "general", 11, 100),
('A', "general", 12, 100),

/* SUB2400 */
('A', "sub2400", 1, 225),
('A', "sub2400", 2, 150),
('A', "sub2400", 3, 120),
('A', "sub2400", 4, 100),

/* SUB2200 */
('A', "sub2200", 1, 150),
('A', "sub2200", 2, 100),

/* CV */
('A', "cv", 1, 500),
('A', "cv", 2, 400),
('A', "cv", 3, 300),
('A', "cv", 4, 200),
('A', "cv", 5, 100),

/* H */
('A', "h", 1, 125),
('A', "h", 2, 125),
('A', "h", 3, 125),
('A', "h", 4, 125),
('A', "h", 5, 125),
('A', "h", 6, 125),
('A', "h", 7, 125),
('A', "h", 8, 125),
('A', "h", 9, 125),
('A', "h", 10, 125),
('A', "h", 11, 125),
('A', "h", 12, 125),
('A', "h", 13, 125),
('A', "h", 14, 125),
('A', "h", 15, 125),
('A', "h", 16, 125),
('A', "h", 17, 125),
('A', "h", 18, 125),
('A', "h", 19, 125),
('A', "h", 20, 125),

/* PREMIOS OPEN B */
/* General */
('B', "general", 1, 2300),
('B', "general", 2, 1200),
('B', "general", 3, 650),
('B', "general", 4, 550),
('B', "general", 5, 500),
('B', "general", 6, 400),
('B', "general", 7, 300),
('B', "general", 8, 250),
('B', "general", 9, 200),
('B', "general", 10, 150),
('B', "general", 11, 100),
('B', "general", 12, 100),

/* CV */
('B', "cv", 1, 500),
('B', "cv", 2, 400),
('B', "cv", 3, 300),
('B', "cv", 4, 200),
('B', "cv", 5, 100),

/* H */
('B', "h", 1, 125),
('B', "h", 2, 125),
('B', "h", 3, 125),
('B', "h", 4, 125),
('B', "h", 5, 125),
('B', "h", 6, 125),
('B', "h", 7, 125),
('B', "h", 8, 125),
('B', "h", 9, 125),
('B', "h", 10, 125),
('B', "h", 11, 125),
('B', "h", 12, 125),
('B', "h", 13, 125),
('B', "h", 14, 125),
('B', "h", 15, 125),
('B', "h", 16, 125),
('B', "h", 17, 125),
('B', "h", 18, 125),
('B', "h", 19, 125),
('B', "h", 20, 125),

/* SUB1800 */
('B', "sub1800", 1, 150),
('B', "sub1800", 2, 100),

/* SUB1600 */
('B', "sub1600", 1, 150),
('B', "sub1600", 2, 100),

/* SUB1400 */
('B', "sub1400", 1, 150),
('B', "sub1400", 2, 100);




/*
use AjedrezOpen;
select * from premio
where categoria = "B"
AND puesto = 1
AND tipo IN ("general", "sub1400", "h", "cv")
order by importe desc, prioridad;

select p.puesto, p.categoria, p.tipo, j.nombre, p.importe, j.rankingI, j.rankingF
from premio p
join jugador j on p.rankingI = j.rankingI and p.categoria = j.categoria
where p.tipo = 'general' and p.categoria = 'B'
order by p.puesto, j.rankingF;

select * from premio p where p.tipo = 'general' and p.categoria = 'A';


update premio set rankingI = null where categoria = 'A';
update premio set rankingI = null where categoria = 'B';

SELECT p.*
FROM premio p
WHERE p.categoria = 'A'
AND p.tipo IN ("General", "sub2400", "sub2200", "h")
AND p.rankingI IS NULL
ORDER BY p.importe DESC, p.prioridad ASC
LIMIT 1;

select * from jugador where nombre like '%Olsson%';

SELECT fide, hotel, cv, categoria, nombre, rankingI, rankingF FROM jugador ORDER BY rakingF;

select * from premio where tipo = 'general' and categoria = 'B';


select p.puesto, j.nombre, p.importe
from premio p
join jugador j on p.rankingI = j.rankingI and p.categoria = j.categoria
where p.tipo = 'general' and p.categoria = 'A'
order by p.puesto, j.rankingF;
*/