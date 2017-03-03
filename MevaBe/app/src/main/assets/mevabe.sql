CREATE TABLE "children" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"p_id"  TEXT,
"c_id"  INTEGER,
"c_name" TEXT,
"c_birth" INTEGER,
"c_gender" INTEGER,
"updated"  INTEGER
)END


CREATE TABLE "vaccines" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"v_id"  VARCHAR(10) NOT NULL UNIQUE,
"v_name" NVARCHAR(30) NOT NULL,
"v_period" NVARCHAR(30) NOT NULL,
"v_period_f"  INTEGER,
"v_period_t"  INTEGER,
"v_short_des" TEXT,
"v_url" TEXT,
"updated"  INTEGER
)END