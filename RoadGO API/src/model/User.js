const {Schema, model} = require('mongoose');
const bcrypt = require('bcrypt');
const saltRounds = 10;

const UserSchema = new Schema({
    firstName : {
        type : String
    },
    lastName : {
        type : String
    },
    phone : {
        type : String
    },
    gender : {
        type : String,
        default: null
    },
    email : {
        type : String,
        unique: true
    },
    password : {
        type : String
    },
    createdAt: {
        type: Date,
        default: Date.now
    }
});

UserSchema.pre('save',function(next){
    if(this.isNew){
        const document = this;

        bcrypt.hash(document.password , saltRounds, (err, hashedPassword) =>{
            if(err){
                next(err);
            }else{
                document.password = hashedPassword;
                next();
            }

        });
    }else{
        next();
    }

});



UserSchema.methods.isCorrectPassword = function(password,callback){
    bcrypt.compare(password, this.password , function(err,same){
        if(err){
            callback(err);

        }else{
            callback(err, same)
        }
    });
}

module.exports = model('user', UserSchema);