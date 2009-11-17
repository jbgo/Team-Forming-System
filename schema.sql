CREATE TABLE users (
		user_id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
		is_prof INTEGER NOT NULL DEFAULT 0, -- 1 if professor, 0 if student
		last_name VARCHAR(50) NOT NULL,
		first_name VARCHAR(50) NOT NULL,
		email VARCHAR(100) NOT NULL,
		password VARCHAR(50) NOT NULL,
		phone VARCHAR(20)
);

CREATE TABLE courses (
		course_id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
		course_number VARCHAR(20) NOT NULL -- e.g. CS-3354-001
);

CREATE TABLE projects (
		project_id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
		course_id INTEGER CONSTRAINT projects_fk REFERENCES courses,
		project_name VARCHAR(50) NOT NULL
);

-- keeps track of which users are assigned which projects
CREATE TABLE project_assignments (
		proj_assign_id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
		project_id INTEGER CONSTRAINT paproj_fk REFERENCES projects,
		user_id INTEGER CONSTRAINT pauser_fk REFERENCES users
);

-- keeps track of which students are on which teams
CREATE TABLE teams (
		team_member_id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
		project_id INTEGER CONSTRAINT teams_proj_fk REFERENCES projects,
		user_id INTEGER CONSTRAINT teams_user_fk REFERENCES users,
		team_number INTEGER NOT NULL
);

-- keeps track of which skills belong to which projects
CREATE TABLE skills (
		id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
		project_id INTEGER CONSTRAINT skill_proj_fk REFERENCES projects,
		name VARCHAR(100) NOT NULL
);

-- keeps track of a students ratings for a particular skill on a particular project
CREATE TABLE ratings (
		id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
		skill_id INTEGER CONSTRAINT rate_skill_fk REFERENCES skills,
		user_id INTEGER CONSTRAINT rate_user_fk REFERENCES users,
		rating INTEGER NOT NULL
);

-- track authenticated sessions
CREATE TABLE sessions (
		id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
		session_id VARCHAR(128) NOT NULL,
		user_id INTEGER CONSTRAINT sess_user_fk REFERENCES users,
		is_prof INTEGER NOT NULL DEFAULT 0, -- to simplify authorization
		login_time TIMESTAMP NOT NULL,
		last_activity TIMESTAMP NOT NULL
);

