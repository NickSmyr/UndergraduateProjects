import argparse

from commands import tune, gradcheck, run

parser = argparse.ArgumentParser(description='Process some integers.')
parser.add_argument('command', type=str, help='The command to execute. Possible values: gradcheck, tune, run')
parser.add_argument('--eps', type=float, help='The epsilon to use for gradcheck')
parser.add_argument('--tol', type=float, help='The tolerance to use for gradcheck')

parser.add_argument('--lr', type=float, help="The learning rate to use for run")
parser.add_argument('--l2-reg', type=float, help="The regularization term for run")
parser.add_argument('--hidden-size', type=int, help="The hidden size of the model for run")
parser.add_argument('--activation', type=str, help="The activation function for run. One of logexp, tanh, cos")

parser.add_argument('--batch_size', type=int, help="The batch size for run")
parser.add_argument('--n-epochs', type=int, help="The number of epochs to execute for run")

parser.add_argument('--dataset', type=str, help="The dataset to use for run. One of mnist, cifar")

args = parser.parse_args()
print("So you have chosen, ", args.command)

command = args.command
if command=="tune":
    print("executing tune")
    tune()
elif command=="gradcheck":
    print("executing grad check")
    if args.tol is None or args.eps is None:
        print("You need to provide both eps and tol args for gradcheck")
        exit(1)
    gradcheck(args.tol, args.eps)

elif command=="run":
    print("executing run")
    necessary_args = ['lr' , 'l2_reg' , 'n_epochs' , 'batch_size' , 'hidden_size' , 'dataset' , 'activation']
    for a in necessary_args:
        if args.__getattribute__(a) is None:
            print("Missing " , a , " argument for run")
            exit(1)
    run(lr=args.lr,
        l2_reg=args.l2_reg,
        n_epochs=args.n_epochs,
        batch_size=args.batch_size,
        hidden_size=args.hidden_size,
        dataset=args.dataset,
        activation=args.activation
        )

else:
    print("Invalid command")