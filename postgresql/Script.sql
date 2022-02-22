CREATE TABLE "user" (
	"user_id" serial NOT NULL,
	"user_first_name" VARCHAR(255) NOT NULL,
	"user_second_name" VARCHAR(255) NOT NULL,
	"user_middle_name" VARCHAR(255) NOT NULL,
	"user_dob" TIMESTAMP NOT NULL,
	"user_email" VARCHAR(255) NOT NULL,
	"user_phone_number" VARCHAR(25) NOT NULL,
    "image_byte" bytea,
	CONSTRAINT "user_pk" PRIMARY KEY ("user_id")
) WITH (
  OIDS=FALSE
);