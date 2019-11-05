CREATE TABLE contact
(
  id bigserial NOT NULL,
  name character varying(255),
  phone character varying(255),
  email character varying(255),
  address1 character varying(255),
  address2 character varying(255),
  address3 character varying(255),
  postal_code character varying(255),
  note character varying(4000),
  CONSTRAINT contact_pkey PRIMARY KEY (id)
);

ALTER TABLE contact OWNER TO barista;

insert into contact (name, phone, email)
values 
('Monkey D. Luffy', '09012345678', 'luffy@strawhatpirat.es'),
('Roronoa Zoro', '09023456789', 'zoro@strawhatpirat.es'),
('Nami', '09034567890', 'nami@strawhatpirat.es'),
('Usopp', '09045678901', 'usopp@strawhatpirat.es'),
('Vinsmoke Sanji', '09056789012', 'sanji@strawhatpirat.es'),
('Tony Tony Chopper', '09067890123', 'chopper@strawhatpirat.es'),
('Nico Robin', '09078901234', 'robin@strawhatpirat.es'),
('Franky', '09089012345', 'franky@strawhatpirat.es'),
('Brook', '09090123456', 'brook@strawhatpirat.es')

-- truncate table contact;
-- select * from contact;